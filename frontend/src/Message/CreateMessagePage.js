import React, { useCallback, useState } from 'react';
import { useDropzone } from 'react-dropzone';
import axios from 'axios';
import CreateMessagePageCSS from './CreateMessagePage.module.css';
import * as XLSX from 'xlsx';
import { useNavigate } from 'react-router-dom';

function CreatePage() {
  const [recipientContacts, setRecipientContacts] = useState([]);
  const [messageText, setMessageText] = useState('');
  const [messageName, setMessageName] = useState('');
  const navigate = useNavigate();

  const onDrop = useCallback((acceptedFiles) => {
    const reader = new FileReader();
  
    reader.onload = () => {
      const fileContent = reader.result;
  
      if (acceptedFiles[0].name.endsWith('.csv')) {
        const emails = fileContent.split(',');
        setRecipientContacts(emails);
      }
  
      if (acceptedFiles[0].name.endsWith('.xlsx')) {
        const workbook = XLSX.read(fileContent, { type: 'binary' });
        const sheetName = workbook.SheetNames[0];
        const sheet = workbook.Sheets[sheetName];
        const data = XLSX.utils.sheet_to_json(sheet, { header: 1 });
        const emails = data.map((row) => row[0]);
        setRecipientContacts(emails);
        console.log(emails)
      }
    };
  
    reader.readAsBinaryString(acceptedFiles[0]);
  }, [setRecipientContacts]);
  

  const handleButtonClick = () => {
    const requestBody = {
        name: messageName,
        messageText: messageText,
        recipientContacts: recipientContacts
    };

    console.log(requestBody);

    // Make the API request to http://localhost:8080/message-service/message/create
    axios.post('http://localhost:8080/message-service/message/create', requestBody)
      .then(response => {
        console.log('API Response:', response.data);
        navigate('/home')
      })
      .catch(error => {
        console.error('API Error:', error);
      });
  };

  const { getRootProps, getInputProps } = useDropzone({
    onDrop,
    accept: ['.csv', '.xls', '.xlsx'],
  });

  return (
    <div className={CreateMessagePageCSS.mainContainer}>
        <div>
            <textarea
            className={CreateMessagePageCSS.nameTextArea}
            value={messageName}
            onChange={(e) => setMessageName(e.target.value)}
            placeholder="Write down messag name"
            >
            Write message name
            </textarea>
        </div>
        <div {...getRootProps()} className={CreateMessagePageCSS.DragNDrop}>
            <input {...getInputProps()} />
            <p>Drag 'n' drop CSV or XLS file here, or click to select files</p>
        </div>
        <div>
          <textarea
              className={CreateMessagePageCSS.messageTextArea}
              value={messageText}
              onChange={(e) => setMessageText(e.target.value)}
              placeholder="Write down messag text"
          />
        </div>
        <button onClick={handleButtonClick} className={CreateMessagePageCSS.inputButton}>Create message</button>
    </div>
  );
}

export default CreatePage;