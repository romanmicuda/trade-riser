import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Link } from "react-router-dom";
import { useForm } from "react-hook-form";
import { useState } from "react";
import { useGlobalContext } from "./contexts/GlobalContext";

const SignUp = () => {
    const [isSubmitting, setIsSubmitting] = useState(false);
    const {
        register,
        handleSubmit,
        watch,
        formState: { errors },
    } = useForm();

    const password = watch("password");
    const { signup } = useGlobalContext();

    const onSubmit = async (data: any) => {
        setIsSubmitting(true);
        await signup(data);
    };

    return (
        <section className="bg-muted h-screen">
            <div className="flex h-full items-center justify-center">
                <div className="flex flex-col items-center gap-6 lg:justify-start">
                    <div className="min-w-sm border-muted bg-background flex w-full max-w-sm flex-col items-center gap-y-4 rounded-md border px-6 py-8 shadow-md">
                        {<h1 className="text-xl font-semibold">Sign Up</h1>}
                        <form onSubmit={handleSubmit(onSubmit)} className="w-full space-y-4">
                            <div className="flex w-full flex-col gap-2">
                                <Label>Email</Label>
                                <Input
                                    {...register("email", {
                                        required: "Email is required",
                                        pattern: {
                                            value: /\S+@\S+\.\S+/,
                                            message: "Invalid email address",
                                        },
                                    })}
                                    type="email"
                                    placeholder="Email"
                                    className="text-sm"
                                />
                                {errors.email && (
                                    <p className="text-sm text-red-500">{errors.email.message}</p>
                                )}
                            </div>
                            
                            <div className="flex w-full flex-col gap-2">
                                <Label>Username</Label>
                                <Input
                                    {...register("username", { required: "Username is required" })}
                                    type="text"
                                    placeholder="Username"
                                    className="text-sm"
                                />
                                {errors.username && (
                                    <p className="text-sm text-red-500">{errors.username.message}</p>
                                )}
                            </div>
                            
                            <div className="flex w-full flex-col gap-2">
                                <Label>Password</Label>
                                <Input
                                    {...register("password", {
                                        required: "Password is required",
                                        minLength: {
                                            value: 8,
                                            message: "Password must be at least 8 characters",
                                        },
                                    })}
                                    type="password"
                                    placeholder="Password"
                                    className="text-sm"
                                />
                                {errors.password && (
                                    <p className="text-sm text-red-500">{errors.password.message}</p>
                                )}
                            </div>
                            
                            <div className="flex w-full flex-col gap-2">
                                <Label>Confirm Password</Label>
                                <Input
                                    {...register("confirmPassword", {
                                        required: "Please confirm your password",
                                        validate: (value) => 
                                            value === password || "Passwords do not match",
                                    })}
                                    type="password"
                                    placeholder="Confirm Password"
                                    className="text-sm"
                                />
                                {errors.confirmPassword && (
                                    <p className="text-sm text-red-500">{errors.confirmPassword.message}</p>
                                )}
                            </div>
                            
                            <Button type="submit" className="w-full" disabled={isSubmitting}>
                                {isSubmitting ? "Creating Account..." : "Create Account"}
                            </Button>
                        </form>
                    </div>
                    <div className="text-muted-foreground flex justify-center gap-1 text-sm">
                        <p>Already a user?</p>
                        <Link
                            to="/signin"
                            className="text-primary font-medium hover:underline"
                        >
                            Login
                        </Link>
                    </div>
                </div>
            </div>
        </section>
    );
};

export { SignUp };
