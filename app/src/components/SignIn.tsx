import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Link } from "react-router";
import { useForm } from "react-hook-form";

type SignInFormValues = {
    username: string;
    password: string;
};

const SignIn = () => {
    const { register, handleSubmit, formState: { errors } } = useForm<SignInFormValues>();
    
    const onSubmit = (data: SignInFormValues) => {
        console.log(data);
        // Handle authentication logic here
    };

    return (
        <section className="bg-muted h-screen">
            <div className="flex h-full items-center justify-center">
                <div className="flex flex-col items-center gap-6 lg:justify-start">
                    <form onSubmit={handleSubmit(onSubmit)} className="min-w-sm border-muted bg-background flex w-full max-w-sm flex-col items-center gap-y-4 rounded-md border px-6 py-8 shadow-md">
                        {<h1 className="text-xl font-semibold">Sign In</h1>}
                        <div className="flex w-full flex-col gap-2">
                            <Label htmlFor="username">Username</Label>
                            <Input
                                id="username"
                                type="text"
                                placeholder="Username"
                                className="text-sm"
                                {...register("username", { required: "Username is required" })}
                            />
                            {errors.username && (
                                <p className="text-destructive text-sm">{errors.username.message}</p>
                            )}
                        </div>
                        <div className="flex w-full flex-col gap-2">
                            <Label htmlFor="password">Password</Label>
                            <Input
                                id="password"
                                type="password"
                                placeholder="Password"
                                className="text-sm"
                                {...register("password", { required: "Password is required" })}
                            />
                            {errors.password && (
                                <p className="text-destructive text-sm">{errors.password.message}</p>
                            )}
                        </div>
                        <Button type="submit" className="w-full">
                                Sign In
                        </Button>
                    </form>
                    <div className="text-muted-foreground flex justify-center gap-1 text-sm">
                        <p>Don't have an account?</p>
                        <Link
                            to="/signup"
                            className="text-primary font-medium hover:underline"
                        >
                            Create Account
                        </Link>
                    </div>
                </div>
            </div>
        </section>
    );
};

export { SignIn };
