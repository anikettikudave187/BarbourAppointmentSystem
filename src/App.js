import './App.css';
import NavBar from './pages/NavBar';
import Login from './pages/Login';
import Register from './pages/Register';
import Home from './pages/Home';
import Shops from './pages/Shops';
import BookShop from './pages/BookShop';
import BookSlot from './pages/BookSlot.js';
import AddShop from './pages/AddShop';
import BePart from './pages/BePart.js';
import UpdateShop from './pages/UpdateShop.js';
import UpdateService from './pages/UpdateService.js';
import BookedSlotsInfo from './pages/BookedSlotsInfo.js';
import AiChatBot from './pages/AiChatBot.js';


import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';



function App() {
  return (
    <Router>
      <NavBar></NavBar>
      <Routes>
        <Route path='/' element={<Home/>}/>
        <Route path='/shops' element={<Shops/>}/>
        <Route path='/bepart' element={<BePart/>}/>
        <Route path='/addShop' element={<AddShop/>}/>
        <Route path='/updateShop/:shopId' element={<UpdateShop/>}/>
        <Route path='/login' element={<Login/>}/>
        <Route path='/register' element={<Register/>}/>
        <Route path= '/shop/:shopId' element={<BookShop/>}/>
        <Route path="/service/:serviceId/bookSlot/:shopId/:date" element={<BookSlot />} />
        <Route path="/bookedSlotsInfo/:shopId/:serviceId/:date" element={<BookedSlotsInfo />} />
        <Route path="/updateService/:serviceId" element={<UpdateService />} />

        
      </Routes>
      <AiChatBot />
    </Router>
    
  );
}

export default App;
