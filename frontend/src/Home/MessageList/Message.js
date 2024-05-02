import React from 'react';
import MessageCSS from './Message.module.css'

const Message = ({ message }) => {
  console.log('Message:', message);
  return (
    <div className={MessageCSS.template}>
      <p>{message}</p>
      <button className={MessageCSS.templateButton}>Update</button>
      <button className={MessageCSS.templateButton}>Delete</button>
    </div>
  )
}

export default Message;
