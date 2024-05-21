import React from 'react';
import MessageCSS from './Message.module.css';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const Message = ({ message}) => {
  console.log('Message:', message);
  const navigate = useNavigate();

  const editPage = (message) => {
    navigate(`/message/edit/${message}`);
  }

  const messagePage = (message) => {
    navigate(`/message/${message}`);
  }

  const deleteMessage = async (message) =>  {
    try {
      const response = await axios.delete(`http://localhost:8080/message-service/message/delete/${message}`);
      console.log('Response:', response.data);
    } catch (error) {
      console.error('Failed to delet messages:', error);
    }
  }

  return (
    <div className={MessageCSS.template}>
      <div className={MessageCSS.messageContent} onClick={() => messagePage(message)}>
        <span>{message}</span>
      </div>
      <div className={MessageCSS.buttonContainer}>
        <button className={MessageCSS.templateButton} onClick={() => editPage(message)}>Edit</button>
        <button className={MessageCSS.templateButton} onClick={() => deleteMessage(message)}>Delete</button>
      </div>
    </div>
  )
}

export default Message;
