import React from 'react';
import HomePageWithLoginCSS from './HomePage.module.css';
import { useNavigate } from 'react-router-dom';
import Message from './MessageList/Message';

const Home = (props) => {
  const navigate = useNavigate();


  const sentMessage = () => {
    navigate('/sentMessage');
  }

  const createMessage = () => {
    navigate('/createMessage'); 
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
          <button className={HomePageWithLoginCSS.inputButton} onClick={createMessage}>Create Message</button>
        </div>
        <div className={HomePageWithLoginCSS.inputButton}>
          <img></img>
        </div>
      </div>
      <div className={HomePageWithLoginCSS.messageContainer}>
        <Message/>
        <Message/>
        <Message/>
        <Message/>
        <Message/>
      </div>
    </div>
  )
}

export default Home