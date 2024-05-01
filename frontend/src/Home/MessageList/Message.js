import React from 'react';
import MessageCSS from './Message.module.css'

const Template = (props) => {
  return (
    <div className={MessageCSS.template}>Some text over here to blur text over here to blur 
        <button className={MessageCSS.templateButton}>Update</button><button className={MessageCSS.templateButton}>Delete</button>
    </div>
  )
}

export default Template