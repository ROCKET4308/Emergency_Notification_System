import React from 'react';
import HomePageWithoutLoginCSS from './HomePageWithoutLogin.module.css';
import { useNavigate } from 'react-router-dom';

const Home = (props) => {
  const navigate = useNavigate();


  const sentMessage = () => {
    navigate('/sentMessage');
  }

  const createMessageTemplate = () => {
    navigate('/createMessageTemplate'); 
  }

  const login = () => {
    navigate('/auth/login'); 
  }

  const register = () => {
    navigate('/auth/register'); 
  }

  return (
    <div className={HomePageWithoutLoginCSS.mainContainer}>
      <div className={HomePageWithoutLoginCSS.buttonContainer}>
        <div className={HomePageWithoutLoginCSS.homeButton}>
          <button className={HomePageWithoutLoginCSS.inputButton}onClick={sentMessage}>Sent Message</button>
        </div>
        <div className={HomePageWithoutLoginCSS.homeButton}>
          <button className={HomePageWithoutLoginCSS.inputButton} onClick={createMessageTemplate}>Create Message Template</button>
        </div>
        <div className={HomePageWithoutLoginCSS.homeButton}>
          <button className={HomePageWithoutLoginCSS.inputButton} onClick={login}>Login</button>
        </div>
        <div className={HomePageWithoutLoginCSS.homeButton}>
          <button className={HomePageWithoutLoginCSS.inputButton} onClick={register}>Register</button>
        </div>
      </div>
      <div className={HomePageWithoutLoginCSS.templateContainer}>
        <div className={HomePageWithoutLoginCSS.template}>Some text over here to blur text over here to blur 
          <button className={HomePageWithoutLoginCSS.templateButton}>Update</button><button className={HomePageWithoutLoginCSS.templateButton}>Delete</button>
        </div>
        <div className={HomePageWithoutLoginCSS.template}>Some text over here to blur text over here to blur 
          <button className={HomePageWithoutLoginCSS.templateButton}>Update</button><button className={HomePageWithoutLoginCSS.templateButton}>Delete</button>
        </div>
        <div className={HomePageWithoutLoginCSS.template}>Some text over here to blur text over here to blur 
          <button className={HomePageWithoutLoginCSS.templateButton}>Update</button><button className={HomePageWithoutLoginCSS.templateButton}>Delete</button>
        </div>
        <div className={HomePageWithoutLoginCSS.template}>Some text over here to blur text over here to blur 
          <button className={HomePageWithoutLoginCSS.templateButton}>Update</button><button className={HomePageWithoutLoginCSS.templateButton}>Delete</button>
        </div>
        <div className={HomePageWithoutLoginCSS.template}>Some text over here to blur text over here to blur 
          <button className={HomePageWithoutLoginCSS.templateButton}>Update</button><button className={HomePageWithoutLoginCSS.templateButton}>Delete</button>
        </div>
      </div>
    </div>
  )
}

export default Home