import { useState, useEffect } from 'react';
import { BrowserRouter, Route, Routes, Navigate } from 'react-router-dom'
import HomePageWithLogin from '../Home/HomePageWithLogin/HomePageWithLogin'
import HomePageWithoutLogin from '../Home/HomePageWithoutLogin/HomePageWithoutLogin'
import AppCSS from'./App.module.css'
import Login from '../Auth/Login/LoginPage'
import Register from '../Auth/Register/RegisterPage'
import SentMessagePage from '../Message/SentMessagePage'
import axios from 'axios';

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  useEffect(() => {
    const verifyToken = async () => {
      try {
        if(localStorage.getItem("token")){
          axios.defaults.headers.common['Authorization'] = `Bearer ${localStorage.getItem("token")}`;
        }
        const verifyResponse = await axios.get('http://localhost:8080/auth/verify');
        setIsLoggedIn(verifyResponse.data);
      } catch (error) {
        console.error('Failed to verify token:', error);
        setIsLoggedIn(false);
      }
    };

    verifyToken();
  }, []);

  return (
    <div className={AppCSS.App}>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Navigate to="/home" />} />
          <Route path="/home" element={isLoggedIn ? <HomePageWithLogin /> : <HomePageWithoutLogin />} />
          <Route path="/auth/login" element={<Login setIsLoggedIn={setIsLoggedIn} />} />
          <Route path="/auth/register" element={<Register setIsLoggedIn={setIsLoggedIn} />} />
          <Route path="/sentMessage" element={<SentMessagePage />} />
        </Routes>
      </BrowserRouter>
    </div>
  )
}

export default App
