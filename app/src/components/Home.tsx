export default function Home() {
    const features = [
        {
            title: 'Portfolio Simulation',
            description: 'Create and test investment strategies with virtual money before risking real capital',
            icon: '📈'
        },
        {
            title: 'Real-Time Market Data',
            description: 'Access live stock and ETF prices, historical charts, and financial metrics',
            icon: '⏱️'
        },
        {
            title: 'Advanced Analytics',
            description: 'Evaluate portfolio performance, risk metrics, and return projections',
            icon: '📊'
        },
        {
            title: 'Personalized Recommendations',
            description: 'Get AI-powered investment ideas tailored to your financial goals',
            icon: '🤖'
        }
    ];

    return (
        <div className="min-h-screen">
            {/* Hero Section */}
            <section className="bg-gradient-to-r from-amber-500 to-yellow-600 text-white py-20">
                <div className="container mx-auto px-4 max-w-6xl">
                    <div className="flex flex-col md:flex-row items-center justify-between">
                        <div className="md:w-1/2 mb-10 md:mb-0">
                            <h1 className="text-4xl md:text-5xl font-bold mb-6">Master the Market with Trade Riser</h1>
                            <p className="text-xl mb-8">Simulate trades, manage portfolios, and analyze investments with professional-grade tools and real-time data.</p>
                            <div className="flex flex-wrap gap-4">
                                <a href="/signup" className="bg-white text-amber-600 px-6 py-3 rounded-md font-medium hover:bg-opacity-90 transition">Get Started Free</a>
                                <a href="/demo" className="bg-transparent border border-white px-6 py-3 rounded-md font-medium hover:bg-white hover:bg-opacity-10 transition">View Demo</a>
                            </div>
                        </div>
                        <div className="md:w-1/2">
                            {/* Placeholder for stock chart or dashboard preview */}
                            <div className="bg-white rounded-lg shadow-xl p-4 w-full aspect-video">
                                <div className="w-full h-full bg-gray-100 rounded flex items-center justify-center">
                                    <span className="text-gray-500">Chart Dashboard Preview</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            {/* Features Section */}
            <section className="py-16 bg-white">
                <div className="container mx-auto px-4 max-w-6xl">
                    <h2 className="text-3xl font-bold text-center mb-12">Platform Features</h2>
                    <div className="grid md:grid-cols-2 lg:grid-cols-4 gap-8">
                        {features.map((feature, index) => (
                            <div key={index} className="bg-white p-6 rounded-lg shadow-md hover:shadow-lg transition border border-amber-100">
                                <div className="text-4xl mb-4">{feature.icon}</div>
                                <h3 className="text-xl font-semibold mb-2">{feature.title}</h3>
                                <p className="text-gray-600">{feature.description}</p>
                            </div>
                        ))}
                    </div>
                </div>
            </section>

            {/* How It Works Section */}
            <section className="py-16 bg-gray-50">
                <div className="container mx-auto px-4 max-w-6xl">
                    <h2 className="text-3xl font-bold text-center mb-12">How Trade Riser Works</h2>
                    <div className="grid md:grid-cols-3 gap-8 text-center">
                        <div>
                            <div className="bg-amber-100 rounded-full w-16 h-16 flex items-center justify-center mx-auto mb-4">
                                <span className="text-amber-600 text-xl font-bold">1</span>
                            </div>
                            <h3 className="text-xl font-semibold mb-2">Create Your Portfolio</h3>
                            <p className="text-gray-600">Sign up and build virtual portfolios with stocks and ETFs of your choice</p>
                        </div>
                        <div>
                            <div className="bg-amber-100 rounded-full w-16 h-16 flex items-center justify-center mx-auto mb-4">
                                <span className="text-amber-600 text-xl font-bold">2</span>
                            </div>
                            <h3 className="text-xl font-semibold mb-2">Simulate Trades</h3>
                            <p className="text-gray-600">Execute buy and sell orders with real-time market prices</p>
                        </div>
                        <div>
                            <div className="bg-amber-100 rounded-full w-16 h-16 flex items-center justify-center mx-auto mb-4">
                                <span className="text-amber-600 text-xl font-bold">3</span>
                            </div>
                            <h3 className="text-xl font-semibold mb-2">Analyze & Optimize</h3>
                            <p className="text-gray-600">Track performance, receive insights, and refine your investment strategy</p>
                        </div>
                    </div>
                </div>
            </section>

            {/* CTA Section */}
            <section className="bg-amber-600 text-white py-16">
                <div className="container mx-auto px-4 max-w-6xl text-center">
                    <h2 className="text-3xl font-bold mb-6">Ready to Elevate Your Trading Skills?</h2>
                    <p className="text-xl mb-8 max-w-3xl mx-auto">Join thousands of investors using Trade Riser to improve their market knowledge and investment strategies.</p>
                    <a href="/signup" className="bg-white text-amber-600 px-8 py-4 rounded-md font-medium hover:bg-opacity-90 transition text-lg">Start Trading Now</a>
                </div>
            </section>
        </div>
    );
}
