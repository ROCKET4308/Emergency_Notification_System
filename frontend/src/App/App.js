import { BrowserRouter, Route, Routes, Navigate } from 'react-router-dom'
import HomePageWithLogin from '../Home/HomePageWithLogin/HomePageWithLogin'
import HomePageWithoutLogin from '../Home/HomePageWithoutLogin/HomePageWithoutLogin'
import AppCSS from'./App.module.css'
import Login from '../Auth/Login/LoginPage'
import Register from '../Auth/Register/RegisterPage'
import SentMessagePage from '../Message/SentMessagePage'

function App() {
  const isLoggedIn = true; // You need to implement this logic to check if the user is logged in

  return (
    <div className={AppCSS.App}>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Navigate to="/home" />} />
          <Route path="/home" element={isLoggedIn ? <HomePageWithLogin /> : <HomePageWithoutLogin />} />
          <Route path="/auth/login" element={<Login />} />
          <Route path="/auth/register" element={<Register />} />
          <Route path="/sentMessage" element={<SentMessagePage />} />
        </Routes>
      </BrowserRouter>
    </div>
  )
}

export default App
