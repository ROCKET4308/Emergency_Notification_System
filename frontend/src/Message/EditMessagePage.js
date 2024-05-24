import { useParams } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { useState, useEffect } from 'react';
import EditMessagePageCSS from './EditMessagePage.module.css';

function EditMessagePage() {
  const { messageName } = useParams();
  const [message, setMessage] = useState(null);
  const [updatedName, setUpdatedName] = useState('');
  const [updatedText, setUpdatedText] = useState('');
  const [updatedContacts, setUpdatedContacts] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const getMessage = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/message-service/message/get/${messageName}`);
        console.log('Response:', response.data);
        setMessage(response.data);
        setUpdatedName(response.data.name);
        setUpdatedText(response.data.messageText);
        setUpdatedContacts(response.data.recipientContacts);
      } catch (error) {
        console.error('Failed to get message:', error);
      }
    };
    getMessage();
  }, [messageName]);

  const edit = async () => {
    try {
      const response = await axios.put(`http://localhost:8080/message-service/message/update/${messageName}`, {
        name: updatedName,
        messageText: updatedText,
        recipientContacts: updatedContacts
      });
      console.log('API Response:', response.data);
      navigate('/home');
    } catch (error) {
      console.error('API Error:', error);
    }
  };

  if (!message) {
    return <div>Loading...</div>;
  }

  return (
    <div className={EditMessagePageCSS.mainContainer}>
      <div className={EditMessagePageCSS.formContainer}>
        <h2>Edit Message</h2>
        <div className={EditMessagePageCSS.inputContainer}>
          <label>
            Name:
            <input 
              type="text" 
              value={updatedName} 
              onChange={(e) => setUpdatedName(e.target.value)} 
              className={EditMessagePageCSS.inputField} 
            />
          </label>
        </div>
        <div className={EditMessagePageCSS.inputContainer}>
          <label>
            Message Text:
            <textarea 
              value={updatedText} 
              onChange={(e) => setUpdatedText(e.target.value)} 
              className={EditMessagePageCSS.inputField} 
            />
          </label>
        </div>
        <div className={EditMessagePageCSS.inputContainer}>
          <label>
            Recipient Contacts (one per line):
            <textarea
              value={updatedContacts.join('\n')}
              onChange={(e) => setUpdatedContacts(e.target.value.split('\n').map(contact => contact.trim()))}
              className={EditMessagePageCSS.inputField}
              style={{ height: '200px' }}
            />
          </label>
        </div>
        <button onClick={edit} className={EditMessagePageCSS.button}>Save</button>
      </div>
    </div>
  );
}


export default EditMessagePage;