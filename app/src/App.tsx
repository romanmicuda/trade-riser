// src/App.jsx or src/App.js
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Home from './components/Home';
import { SignIn } from './components/SignIn';
import { SignUp } from './components/SignUp';
import Dashboard from './components/Dashboard';
import NotFound from './components/NotFound';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/signin" element={<SignIn />} />
        <Route path="/signup" element={<SignUp />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="*" element={<NotFound />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
