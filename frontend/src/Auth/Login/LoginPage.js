import React, { useState } from 'react';
import LoginPageCSS from './LoginPage.module.css';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
 
const Login = (props) => {
  const { setIsLoggedIn } = props
 
  const [input, setInput] = useState({
    email: '',
    password: '',
  });
 
  const [error, setError] = useState({
    email: '',
    password: '',
  })

  const navigate = useNavigate();
 
  const onInputChange = e => {
    const { name, value } = e.target;
    setInput(prev => ({
      ...prev,
      [name]: value
    }));
    validateInput(e);
  }

  const onButtonClick = async () => {
    const requestBody = {
      email: input.email,
      password: input.password,
    };
  
    try {
      // Make the API request to http://localhost:8080/auth-service/auth/login
      delete axios.defaults.headers.common['Authorization'];
      const response = await axios.post('http://localhost:8080/auth-service/auth/login', requestBody);
      console.log('API Response:', response.data);
      const token = response.data.token;
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
      localStorage.setItem("token", token);

      const verifyResponse = await axios.get('http://localhost:8080/auth-service/auth/verify');
      if (verifyResponse.data) {
        setIsLoggedIn(true);
      } else {
        setIsLoggedIn(false);
      }
      navigate('/home');
    } catch (error) {
      console.error('API Error:', error);
    }
  };
  
  
  
 
  const validateInput = e => {
    let { name, value } = e.target;
    setError(prev => {
      const stateObj = { ...prev, [name]: "" };
 
      switch (name) {
        case "email":
          if (!value) {
            stateObj[name] = "Enter email";
          } else if (!isValidEmail(value)) {
            stateObj[name] = "Invalid email format";
          }
          break;
 
        case "password":
          if (!value) {
            stateObj[name] = "Enter password";
          }
          break;
 
        default:
          break;
      }
 
      return stateObj;
    });
  }

  const register = () => {
    navigate('/auth/register'); 
  }

  const isValidEmail = (email) => {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
  }
 
  return (
    <div className={LoginPageCSS.app}>
      <h4>Login</h4>
      <div className={LoginPageCSS.form}>
 
        <input
          type="text"
          name="email"
          placeholder='Enter Email'
          value={input.email}
          onChange={onInputChange}
          onBlur={validateInput}></input>
        {error.email && <span className={LoginPageCSS.err}>{error.email}</span>}
 
        <input
          type="password"
          name="password"
          placeholder='Enter Password'
          value={input.password}
          onChange={onInputChange}
          onBlur={validateInput}></input>
        {error.password && <span className={LoginPageCSS.err}>{error.password}</span>}
 
        <button onClick={onButtonClick}>Submit</button>
        <p>-------------- or --------------</p>
        <button onClick={register}>Register</button>
      </div>
    </div>
  );
}
 
export default Login;