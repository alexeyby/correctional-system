import { useState, useEffect, useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import Select from 'react-select';
import { Modal } from 'bootstrap';
import { toast } from 'react-toastify';
import PatientService from '../../services/PatientService';
import Loading from '../loading/Loading';
import DeleteModal from '../modal/DeleteModal';
import styles from './PatientsList.module.css';

const mixtures = [
    { value: 'enteral', label: 'Энтерал' },
    { value: 'peptomen', label: 'Пептомен' }
];

const reportSelectStyles = {
    container: (base) => ({
        ...base,
        width: '25%',
        minWidth: '200px'
    }),
    control: (base, state) => ({
        ...base,
        borderColor: state.isFocused ? '#a980ca' : '#cccccc',
        boxShadow: state.isFocused ? '0 0 0 4px #b38cd39f' : 'none',
        '&:hover': {
            borderColor: state.isFocused ? '#a980ca' : '#cccccc'
        }
    }),
    option: (provided, state) => ({
      ...provided,
      color: state.isSelected ? 'white' : 'black',
      backgroundColor: state.isSelected ? '#b58ad8' : 'white',
      '&:hover': {
        backgroundColor: !state.isSelected && '#f3e8fa',
          color: !state.isSelected && 'black'
      },
      '&:active': {
          backgroundColor: '#f3e8fa',
          color: 'black'
      }
    }),
    indicatorSeparator : () => null
};

function PatientsList() {
    const navigate = useNavigate();

    const deleteModalRef = useRef();

    const [{ isLoading, showLoading, patients, activeRow }, setState] = useState(
            {
                isLoading: true,
                showLoading: false,
                patients: '',
                activeRow: ''
            }          
          );

    const [selectedMixture, setSelectedMixture] = useState('');

    const [historyNumberFilter, setHistoryNumberFilter] = useState(''),
          [lastNameFilter, setLastNameFilter] = useState(''),
          [firstNameFilter, setFirstNameFilter] = useState(''),
          [surnameFilter, setSurnameFilter] = useState('');

    useEffect(() => {
        PatientService.getPatients().then((res) => {
            setState((state) => {
                return { ...state, isLoading: false, showLoading: false, patients: res.data };
            });
        }).catch(() => {
            console.log('what');
        });
        setTimeout(() => {
            setState((state) => {
                return { ...state, showLoading: true };
            });
        }, 100);
    }, []);

    function historyNumberFilterHandler(event) {
        setHistoryNumberFilter(event.target.value);
    };

    function lastNameFilterHandler(event) {
        setLastNameFilter(event.target.value);
    };

    function firstNameFilterHandler(event) {
        setFirstNameFilter(event.target.value);
    };

    function surnameFilterHandler(event) {
        setSurnameFilter(event.target.value);
    };

    function selectedMixtureHandler(event) {
        setSelectedMixture(event);
    }

    function addPatient() {
        navigate('/add-patient');
    };

    function deletePatient() {
        PatientService.deletePatient(activeRow).then(() => {
            let newPatients = patients.filter((patient) => { return patient.patientUuid !== activeRow });
            
            setState((state) => {
                return { ...state, patients: newPatients };
            });
    
            hideDeleteModal();
    
            toast.success('Пациент успешно удалён');
        });
    };

    function getPatient(id) {
        navigate(`/get-patient/${id}`);
    };

    function chooseRow(event, currentActiveRow) {
        let radio = event.currentTarget.firstElementChild.firstElementChild;
        radio.checked = !radio.checked;
        
        let newActiveRow = activeRow !== currentActiveRow ? currentActiveRow : '';
  
        setState((state) => {
            return { ...state, activeRow: newActiveRow };
        });
    };  

    function showDeleteModal() {
        let deleteModal = new Modal(deleteModalRef.current);
        deleteModal.show();
    };

    function hideDeleteModal() {
        let deleteModal = Modal.getInstance(deleteModalRef.current);
        deleteModal.hide();
    };

    function generateReport() {
        let activePatient = patients.find((patient) => {
            return patient.patientUuid === activeRow;
        });
        activePatient.hasCheckup 
            ?
                navigate(`/gen-report/${selectedMixture.value}/${activeRow}`)
            :
                toast.info('Добавьте обследование');
    };

    return (
        <>
            { 
                !isLoading &&
                <div className='container-fluid'>
                    <div className='row mt-5 mb-4'>
                        <div className='col'>
                            <h2 className='text-center'>Пациенты</h2>
                        </div>
                    </div>
                    <div className='row mb-2'>
                        <div className={'col mb-2 ' + styles.col__filter}>
                            <label className={'form-label ' + styles.form_label__position} htmlFor='historyNumber'>История болезни №:</label>
                            <input placeholder='0123456789' className={'form-control ' + styles.input__filter} id='historyNumber' 
                                onChange={(event) => historyNumberFilterHandler(event)} />
                        </div>
                        <div className={'col mb-2 ' + styles.col__filter}>
                            <label className={'form-label ' + styles.form_label__position} htmlFor='lastName'>Фамилия:</label>
                            <input placeholder='Иванов' className={'form-control ' + styles.input__filter} id='lastName'
                                onChange={(event) => lastNameFilterHandler(event)} />
                        </div>
                        <div className={'col mb-2 ' + styles.col__filter}>
                            <label className={'form-label ' + styles.form_label__position} htmlFor='firstName'>Имя:</label>
                            <input placeholder='Иван' className={'form-control ' + styles.input__filter} id='firstName' 
                                onChange={(event) => firstNameFilterHandler(event)} />
                        </div>
                        <div className={'col mb-2 ' + styles.col__filter}>
                            <label className={'form-label ' + styles.form_label__position} htmlFor='surname'>Отчество:</label>
                            <input placeholder='Иванович' className={'form-control ' + styles.input__filter} id='surname' 
                                onChange={(event) => surnameFilterHandler(event)} />
                        </div>
                    </div>
                    <div className='row mb-2'>
                        <div className='d-flex justify-content-end gap-1 flex-wrap'>
                            <button className={'btn ' + styles.btn__list + ' ' + styles.btn__focused} 
                                onClick={addPatient}>Добавить</button>
                            <button type='button' className={'btn ' + styles.btn__list + ' ' + styles.btn__focused} 
                                onClick={showDeleteModal} disabled={!activeRow}>Удалить</button>
                            <DeleteModal ref={deleteModalRef} onAccept={deletePatient} onCancel={hideDeleteModal} />
                        </div>
                    </div>
                    <div className='row mb-2'>
                        <div className='col'>
                            <table className={'table table-bordered ' + styles.table__list}>
                                <thead className={styles.thead__list}>
                                    <tr className={styles.tr__list + ' ' + styles.tr__head}>
                                        <th className={styles.column__radio} />
                                        <th className={styles.th__list}>№</th>
                                        <th className={styles.th__list}>Фамилия</th>
                                        <th className={styles.th__list}>Имя</th>
                                        <th className={styles.th__list}>Отчество</th>
                                        <th className={styles.column__object} />
                                    </tr>
                                </thead>
                                <tbody className={styles.tbody__list}>
                                    {
                                        patients
                                        .filter((patient) =>
                                            patient.historyNumber.match(`^${historyNumberFilter}.*`) &&
                                            patient.lastName.match(`^${lastNameFilter}.*`) &&
                                            patient.firstName.match(`^${firstNameFilter}.*`) &&
                                            patient.surname.match(`^${surnameFilter}.*`)
                                        )
                                        .map((patient) =>
                                            <tr className={styles.tr__list + ' ' + styles.tr__hoovered} key={patient.patientUuid} 
                                                onClick={(event) => {chooseRow(event, patient.patientUuid)}}>
                                                <td className={styles.column__radio}>
                                                    <input className={'form-check-input ' + styles.radio} type='radio' name='activeRow' 
                                                        disabled />
                                                 </td>
                                                <td className={styles.td__list +' ' + styles.column__number}>{patient.historyNumber}</td>
                                                <td className={styles.td__list}>{patient.lastName}</td>
                                                <td className={styles.td__list}>{patient.firstName}</td>
                                                <td className={styles.td__list}>{patient.surname}</td>
                                                <td className={styles.column__object}>
                                                    <button className={'btn ' + styles.btn__focused} 
                                                        onClick={() => getPatient(patient.patientUuid)}>&gt;</button>
                                                </td>
                                            </tr>
                                        )
                                    }
                                </tbody>
                            </table>
                          </div>
                        </div>
                        <div className='row'>
                            <div className='d-flex justify-content-end gap-2 flex-wrap'>
                                <Select styles={reportSelectStyles} placeholder='Выберите смесь...'
                                    options={mixtures} value={selectedMixture} 
                                    onChange={(event) => selectedMixtureHandler(event)} />
                                <button className={'btn ' + styles.btn__report + ' ' + styles.btn__focused} onClick={generateReport} 
                                    disabled={!activeRow || !selectedMixture}>Создать отчёт</button>
                        </div>
                    </div>
                </div>
            }
            { 
                isLoading && showLoading && <Loading /> 
            }
            { 
                isLoading && !showLoading && <div className='vh-100' /> 
            }
        </>
    )
}

export default PatientsList;