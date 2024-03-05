import React, { useState } from 'react';
import LoginPageCSS from './LoginPage.module.css';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
 
function Login() {
 
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

  const onButtonClick = () => {
  
    const requestBody = {
      email: input.email,
      password: input.password,
    };
  
    // Make the API request to http://localhost:8080/auth/login
    axios.post('http://localhost:8080/auth/login', requestBody)
      .then(response => {
        console.log('API Response:', response.data);
        navigate('/withLogin');
      })
      .catch(error => {
        console.error('API Error:', error);
      });
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

  const isValidEmail = (email) => {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
  }
 
  return (
    <div className={LoginPageCSS.app}>
      <h4>Registration</h4>
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
      </div>
    </div>
  );
}
 
export default Login;