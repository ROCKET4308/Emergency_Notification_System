import React, { useCallback, useState } from 'react';
import { useDropzone } from 'react-dropzone';
import axios from 'axios';
import SentMessagePageCSS from './SentMessagePage.module.css';
import * as XLSX from 'xlsx';

function SentPage() {
  const [mailList, setMailList] = useState([]);
  const [messageText, setMessageText] = useState('');

  const onDrop = useCallback((acceptedFiles) => {
    const reader = new FileReader();
  
    reader.onload = () => {
      const fileContent = reader.result;
  
      if (acceptedFiles[0].name.endsWith('.csv')) {
        const emails = fileContent.split(',');
        setMailList(emails);
      }
  
      if (acceptedFiles[0].name.endsWith('.xlsx')) {
        const workbook = XLSX.read(fileContent, { type: 'binary' });
        const sheetName = workbook.SheetNames[0];
        const sheet = workbook.Sheets[sheetName];
        const data = XLSX.utils.sheet_to_json(sheet, { header: 1 });
        const emails = data.map((row) => row[0]);
        setMailList(emails);
        console.log(emails)
      }
    };
  
    reader.readAsBinaryString(acceptedFiles[0]);
  }, [setMailList]);
  

  const handleButtonClick = () => {
    const requestBody = {
      massageText: messageText,
      contacts: mailList,
    };

    console.log(requestBody);

    // Make the API request to http://localhost:8080/massage/mail
    axios.post('http://localhost:8080/massage/mail', requestBody)
      .then(response => {
        console.log('API Response:', response.data);
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
    <div className={SentMessagePageCSS.SentMassagePage}>
      <div {...getRootProps()} className={SentMessagePageCSS.DragNDrop}>
        <input {...getInputProps()} />
        <p>Drag 'n' drop CSV or XLS file here, or click to select files</p>
      </div>
      <div className={SentMessagePageCSS.TextArea}>
        <textarea
          value={messageText}
          onChange={(e) => setMessageText(e.target.value)}
        >
          Write down some text
        </textarea>
      </div>
      <button onClick={handleButtonClick}>Sent notification</button>
    </div>
  );
}

export default SentPage;
