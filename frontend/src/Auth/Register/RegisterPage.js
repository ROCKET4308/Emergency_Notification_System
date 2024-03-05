import React, { useState } from 'react';
import RegisterPageCSS from './RegisterPage.module.css';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
 
function Register() {
 
  const [input, setInput] = useState({
    email: '',
    password: '',
    confirmPassword: ''
  });
 
  const [error, setError] = useState({
    email: '',
    password: '',
    confirmPassword: ''
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
    if (input.password !== input.confirmPassword) {
      console.log('Passwords do not match');
      return;
    }
  
    const requestBody = {
      email: input.email,
      password: input.password,
    };
  
    // Make the API request to http://localhost:8080/auth/register
    axios
      .post('http://localhost:8080/auth/register', requestBody)
      .then((response) => {
        console.log('API Response:', response.data);
        navigate('/withLogin');
      })
      .catch((error) => {
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
          } else if (input.confirmPassword && value !== input.confirmPassword) {
            stateObj["confirmPassword"] = "Passwords does not match";
          } else {
            stateObj["confirmPassword"] = input.confirmPassword ? "" : error.confirmPassword;
          }
          break;
 
        case "confirmPassword":
          if (!value) {
            stateObj[name] = "Confirm Password";
          } else if (input.password && value !== input.password) {
            stateObj[name] = "Passwords does not match";
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
    <div className={RegisterPageCSS.app}>
      <h4>Registration</h4>
      <div className={RegisterPageCSS.form}>
 
        <input
          type="text"
          name="email"
          placeholder='Enter Email'
          value={input.email}
          onChange={onInputChange}
          onBlur={validateInput}></input>
        {error.email && <span className={RegisterPageCSS.err}>{error.email}</span>}
 
        <input
          type="password"
          name="password"
          placeholder='Enter Password'
          value={input.password}
          onChange={onInputChange}
          onBlur={validateInput}></input>
        {error.password && <span className={RegisterPageCSS.err}>{error.password}</span>}
 
        <input
          type="password"
          name="confirmPassword"
          placeholder='Enter Confirm Password'
          value={input.confirmPassword}
          onChange={onInputChange}
          onBlur={validateInput}></input>
        {error.confirmPassword && <span className={RegisterPageCSS.err}>{error.confirmPassword}</span>}
 
        <button onClick={onButtonClick}>Submit</button>
      </div>
    </div>
  );
}
 
export default Register;