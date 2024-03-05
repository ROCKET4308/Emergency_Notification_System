import React from 'react';
import HomePageWithLoginCSS from './HomePageWithLogin.module.css';
import { useNavigate } from 'react-router-dom';
import Template from '../TemplateList/Template';

const Home = (props) => {
  const navigate = useNavigate();


  const sentMessage = () => {
    navigate('/sentMessage');
  }

  const createMessageTemplate = () => {
    navigate('/createMessageTemplate'); 
  }
  //TODO: set same icon to show avatar of user
  //TODO: show user real template or write that there is no template for user

  return (
    <div className={HomePageWithLoginCSS.mainContainer}>
      <div className={HomePageWithLoginCSS.buttonContainer}>
        <div className={HomePageWithLoginCSS.homeButton}>
          <button className={HomePageWithLoginCSS.inputButton} onClick={sentMessage}>Sent Message</button>
        </div>
        <div className={HomePageWithLoginCSS.homeButton}>
          <button className={HomePageWithLoginCSS.inputButton} onClick={createMessageTemplate}>Create Message Template</button>
        </div>
        <div className={HomePageWithLoginCSS.inputButton}>
          <img></img>
        </div>
      </div>
      <div className={HomePageWithLoginCSS.templateContainer}>
        <Template/>
        <Template/>
        <Template/>
        <Template/>
        <Template/>
      </div>
    </div>
  )
}

export default Home