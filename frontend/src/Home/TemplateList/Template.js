import React from 'react';
import TemplateCSS from './Template.module.css'

const Template = (props) => {
  return (
    <div className={TemplateCSS.template}>Some text over here to blur text over here to blur 
        <button className={TemplateCSS.templateButton}>Update</button><button className={TemplateCSS.templateButton}>Delete</button>
    </div>
  )
}

export default Template