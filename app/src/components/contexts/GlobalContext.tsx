import React, { createContext, useContext, useState, type ReactNode, useEffect } from 'react';
import { api } from '../utils/routes';

interface GlobalContextType {
    isLoading: boolean;
    setIsLoading: (loading: boolean) => void;
    signup: (data: any) => Promise<any>;
    signin: (data: any) => Promise<any>;
    logout: () => void;
    checkToken: () => Promise<boolean>;
    resetPassword: (email: string) => Promise<void>;
    verifyResetCode: (code: string) => Promise<void>;
    updatePassword: (newPassword: string) => Promise<void>;
}

const GlobalContext = createContext<GlobalContextType | undefined>(undefined);

interface GlobalProviderProps {
    children: ReactNode;
}

export const GlobalProvider: React.FC<GlobalProviderProps> = ({ children }) => {
    const [isLoading, setIsLoading] = useState<boolean>(false);
    const [email, setEmail] = useState<string>('');
    const [code, setCode] = useState<string>('');

    
    // Check token validity on mount
    useEffect(() => {
        const validateToken = async () => {
            if ((window.location.pathname !== '/signin') && (window.location.pathname !== '/') && 
            (window.location.pathname !== '/signup') && (window.location.pathname !== '/reset-password') &&
            (window.location.pathname !== '/update-password') && (window.location.pathname !== '/verify-reset-code')) {
                const isValid = await checkToken();
                if (!isValid) {
                    logout();
                }
            }
        };
        
        validateToken();
    }, []);

    const signup = async (data: any) => {
        setIsLoading(true);
        try {
            const response = await api.post('/api/auth/signup', data);
            if (response.status === 200) {
                window.location.href = '/signin';
            }
        } catch (error) {
            throw error;
        } finally {
            setIsLoading(false);
        }
    };

    const signin = async (data: any) => {
        setIsLoading(true);
        try {
            const response = await api.post('/api/auth/signin', data);
            if (response.status === 200) {
                api.setToken(response.data.token);
                window.location.href = '/dashboard';
            }
        } catch (error) {
            throw error;
        } finally {
            setIsLoading(false);
        }
    };
    
    const logout = () => {
        api.clearToken();
        window.location.href = '/signin';
    };
    
    const checkToken = async (): Promise<boolean> => {
        try {
            // If there's no token, return false
            const token = api.getToken();

            if (!token){
                return false;
            }
            
            const response = await api.post('/api/auth/verify', { token });
            return response.status === 200
        } catch (error) {
            // If verification fails, log out
            logout();
            return false;
        }
    };

    const resetPassword = async (email: string) => {
        setIsLoading(true);
        try {
            const response = await api.post('/api/auth/resetPassword', { email });
            if (response.status === 200) {
                setEmail(email);
                window.location.href = '/verify-reset-code';
            }
        } catch (error) {
            throw error;      
        } finally {
            setIsLoading(false);
        }
    };  

    const verifyResetCode = async (code: string) => {
        setIsLoading(true);
        try {
            const response = await api.post('/api/auth/verifyResetCode', { email, code});
            if (response.status === 200) {
                setCode(code);
                window.location.href = '/reset-password/new';
            }
        } catch (error) {
            throw error;
        } finally {
            setIsLoading(false);
        }
    };

    const updatePassword = async (newPassword: string) => {
        setIsLoading(true);
        try {
            const response = await api.post('/api/auth/updatePassword', { email, code, newPassword });
            if (response.status === 200) {
                window.location.href = '/signin';
            }
        } catch (error) {
            throw error;
        } finally {
            setIsLoading(false);
        }
    };

    // Provide the context value
    const value: GlobalContextType = {
        isLoading,
        setIsLoading,
        signup,
        signin,
        logout,
        checkToken,
        resetPassword,
        updatePassword,
        verifyResetCode
    };

    return <GlobalContext.Provider value={value}>{children}</GlobalContext.Provider>;
}

// Custom hook to use the context
export const useGlobalContext = (): GlobalContextType => {
    const context = useContext(GlobalContext);
    if (context === undefined) {
        throw new Error('useGlobalContext must be used within a GlobalProvider');
    }
    return context;
};

export default GlobalProvider;