import React, { createContext, useContext, useState, type ReactNode } from 'react';
import { api } from '../utils/routes';

interface GlobalContextType {
    isLoading: boolean;
    setIsLoading: (loading: boolean) => void;
    signup: (data: any) => Promise<any>;
    signin: (data: any) => Promise<any>;
}

const GlobalContext = createContext<GlobalContextType | undefined>(undefined);

interface GlobalProviderProps {
    children: ReactNode;
}

export const GlobalProvider: React.FC<GlobalProviderProps> = ({ children }) => {
    const [isLoading, setIsLoading] = useState<boolean>(false);

    const signup = async (data: any) => {
        setIsLoading(true);
        try {
            const response = await api.post('/api/auth/signup', data);
            if (response.status === 200) {
                api.setToken(response.data.token);
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
                window.location.href = '/dashboard';
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
        signin
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