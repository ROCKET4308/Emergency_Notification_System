import { useState, useEffect } from 'react';
import { BrowserRouter, Route, Routes, Navigate } from 'react-router-dom'
import HomePage from '../Home/HomePage'
import AppCSS from'./App.module.css'
import Login from '../Auth/Login/LoginPage'
import Register from '../Auth/Register/RegisterPage'
import MessagePage from '../Message/MessagePage'
import CreateMessagePage from '../Message/CreateMessagePage'
import EditMessagePage from '../Message/EditMessagePage'
import axios from 'axios';

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  useEffect(() => {
    const verifyToken = async () => {
      try {
        if(localStorage.getItem("token")){
          axios.defaults.headers.common['Authorization'] = `Bearer ${localStorage.getItem("token")}`;
        }
        const verifyResponse = await axios.get('http://localhost:8080/auth-service/auth/verify');
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
          <Route path="/home" element={isLoggedIn ? <HomePage /> : <Navigate to="/auth/login" /> }/>
          <Route path="/auth/login" element={isLoggedIn ? <Navigate to="/home" /> : <Login setIsLoggedIn={setIsLoggedIn} />} />
          <Route path="/auth/register" element={<Register setIsLoggedIn={setIsLoggedIn} />} />
          <Route path="/message" element={<MessagePage />} />
          <Route path="/message/create" element={<CreateMessagePage />} />
          <Route path="/message/edit" element={<EditMessagePage />} />
        </Routes>
      </BrowserRouter>
    </div>
  )
}

export default App
