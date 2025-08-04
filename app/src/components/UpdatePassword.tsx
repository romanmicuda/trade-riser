import React, { useState } from 'react';
import { useForm, type SubmitHandler } from 'react-hook-form';
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { api } from './utils/routes';
import { useGlobalContext } from './contexts/GlobalContext';

type FormValues = {
    newPassword: string;
    confirmPassword: string;
};

interface UpdatePasswordProps {
    heading?: string;
    logo?: {
        url: string;
        src: string;
        alt: string;
        title?: string;
    };
    buttonText?: string;
    loginText?: string;
    loginUrl?: string;
}

const UpdatePassword: React.FC<UpdatePasswordProps> = ({
    heading = "Update Password",
    buttonText = "Update Password",
    loginText = "Remember your password?",
    loginUrl = "/signin"
}) => {
    const [isSubmitting, setIsSubmitting] = useState<boolean>(false);
    const [message, setMessage] = useState<{ text: string; type: 'success' | 'error' } | null>(null);
    const { 
        register, 
        handleSubmit, 
        reset,
        watch,
        formState: { errors } 
    } = useForm<FormValues>();

    const { updatePassword } = useGlobalContext();

    const onSubmit: SubmitHandler<FormValues> = async (data) => {
        await updatePassword(data.newPassword)
    };

    return (
        <section className="bg-muted h-screen">
            <div className="flex h-full items-center justify-center">
                <div className="flex flex-col items-center gap-6 lg:justify-start">
                    <div className="min-w-sm border-muted bg-background flex w-full max-w-sm flex-col items-center gap-y-4 rounded-md border px-6 py-8 shadow-md">
                        {heading && <h1 className="text-xl font-semibold">{heading}</h1>}
                        <p className="text-muted-foreground text-center text-sm">
                            Enter your email, reset code, and new password to update your account.
                        </p>
                        
                        <form onSubmit={handleSubmit(onSubmit)} className="w-full space-y-4">
                            
                            <div className="flex w-full flex-col gap-2">
                                <Label htmlFor="newPassword">New Password</Label>
                                <Input
                                    id="newPassword"
                                    type="password"
                                    placeholder="Enter new password"
                                    disabled={isSubmitting}
                                    className="text-sm"
                                    {...register("newPassword", { 
                                        required: "New password is required",
                                        minLength: {
                                            value: 8,
                                            message: "Password must be at least 8 characters"
                                        }
                                    })}
                                />
                                {errors.newPassword && (
                                    <p className="text-destructive text-sm">{errors.newPassword.message}</p>
                                )}
                            </div>
                            
                            <div className="flex w-full flex-col gap-2">
                                <Label htmlFor="confirmPassword">Confirm Password</Label>
                                <Input
                                    id="confirmPassword"
                                    type="password"
                                    placeholder="Confirm new password"
                                    disabled={isSubmitting}
                                    className="text-sm"
                                    {...register("confirmPassword", { 
                                        required: "Please confirm your password",
                                        validate: value => value === watch('newPassword') || "Passwords do not match"
                                    })}
                                />
                                {errors.confirmPassword && (
                                    <p className="text-destructive text-sm">{errors.confirmPassword.message}</p>
                                )}
                            </div>
                            
                            {message && (
                                <div className={`p-3 rounded ${message.type === 'success' ? 'bg-green-50 text-green-700' : 'bg-red-50 text-red-700'}`}>
                                    {message.text}
                                </div>
                            )}
                            
                            <Button 
                                type="submit" 
                                className="w-full"
                                disabled={isSubmitting}
                            >
                                {isSubmitting ? 'Updating Password...' : buttonText}
                            </Button>
                        </form>
                    </div>
                    <div className="text-muted-foreground flex justify-center gap-1 text-sm">
                        <p>{loginText}</p>
                        <a
                            href={loginUrl}
                            className="text-primary font-medium hover:underline"
                        >
                            Sign In
                        </a>
                    </div>
                </div>
            </div>
        </section>
    );
};

export default UpdatePassword;