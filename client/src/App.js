import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { ToastContainer, cssTransition } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import Header from './components/header/Header';
import Footer from './components/footer/Footer';
import PatientsList from './components/list/PatientsList';
import DataForm from './components/form/DataForm';
import Report from './components/report/Report';
import styles from './App.module.css';

const toastTransition = cssTransition({
    enter: styles.toast_transition__enter,
    exit: styles.toast_transition__exit,
});

function App() {
  return (
    <>
        <Router>
            <div className={styles.content}>
                <Header />
                <main className={styles.main}>
                    <div className={'container ' + styles.main_container}>
                        <Routes>
                            <Route path='/' element={<PatientsList />} />
                            <Route path='/add-patient' element={<DataForm mode='create' />} />
                            <Route path='/get-patient/:id' element={<DataForm mode='display' />} />
                            <Route path='/gen-report/:mixture/:id' element={<Report />} />
                        </Routes> 
                    </div>
                </main>
                <Footer />
            </div>
        </Router>
        <ToastContainer 
            className='text-center'
            position='bottom-center'
            icon={false}
            closeButton={false} 
            hideProgressBar 
            pauseOnHover={false} 
            pauseOnFocusLoss={false}
            draggable={false}
            autoClose={1000}
            transition={toastTransition}
            />
    </>
  );
}

export default App;