import React, { useCallback, useState } from 'react';
import { useDropzone } from 'react-dropzone';
import axios from 'axios'; // Make sure to install axios using `npm install axios`
import './SentPage.css';
import * as XLSX from 'xlsx';

function SentPage() {
  const [mailList, setMailList] = useState([]);
  const [messageText, setMessageText] = useState('');

  const onDrop = useCallback(acceptedFiles => {
    const reader = new FileReader();

    reader.onload = () => {
      const fileContent = reader.result;

      // Check if the file is CSV
      if (acceptedFiles[0].name.endsWith('.csv')) {
        const emails = fileContent.split(',');
        setMailList(emails);
      }

      // Check if the file is XLSX
      if (acceptedFiles[0].name.endsWith('.xlsx')) {
        const workbook = XLSX.read(fileContent, { type: 'binary' });
        const sheetName = workbook.SheetNames[0];
        const sheet = workbook.Sheets[sheetName];
        const data = XLSX.utils.sheet_to_json(sheet);
        
        // Assuming the emails are in the first column of the first sheet
        const emails = data.map(row => row['A']);
        setMailList(emails);
        console.log(mailList);
      }
    };

    reader.readAsBinaryString(acceptedFiles[0]);
  }, []);

  const handleButtonClick = () => {

    // Create the API request body
    const requestBody = {
      massageText: messageText,
      contacts: mailList,
    };

    // Make the API request to http://localhost:8080/massage/mail
    axios.post('http://localhost:8080/massage/mail', requestBody)
      .then(response => {
        // Handle the API response if needed
        console.log('API Response:', response.data);
      })
      .catch(error => {
        // Handle errors from the API request
        console.error('API Error:', error);
      });
  };

  const { getRootProps, getInputProps } = useDropzone({
    onDrop,
    accept: ['.csv', '.xls', '.xlsx'],
  });

  return (
    <div className="SentPage">
      <div {...getRootProps()} className="DragNDrop">
        <input {...getInputProps()} />
        <p>Drag 'n' drop CSV or XLS file here, or click to select files</p>
      </div>
      <div className="TextArea">
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
