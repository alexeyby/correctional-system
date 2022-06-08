import { useState, useEffect, useRef } from 'react';
import { useParams } from 'react-router-dom';
import Switch from 'react-switch';
import { CSSTransition } from 'react-transition-group';
import { Modal } from 'bootstrap';
import { toast } from 'react-toastify';
import PatientService from '../../services/PatientService';
import CheckupService from '../../services/CheckupService';
import PatientForm from './PatientForm';
import CheckupForm from './CheckupForm';
import Loading from '../loading/Loading';
import DeleteModal from '../modal/DeleteModal';
import styles from './DataForm.module.css';

const initialPatient = {
    patientUuid: '',
    historyNumber: '',
    lastName: '',
    firstName: '',
    surname: '',
    birthdate: '',
    sex: '',
    failure: '',
    hasCheckup: ''
};

const initialCheckup = {
    checkupUuid: '',
    weight: '',
    height: '',
    skinfoldThickness: '',
    armCircumference: '',
    albumin: '',
    transferrin: '',
    lymphocyte: '',
    skinTest: '',
    mobility: '',
    temperature: '',
    injury: '',
    checkupDate: new Date(),
    healthFacility: '',
    patientUuid: ''
};

function DataForm(props) {
    const mode = props.mode;

    const params = useParams();

    const id = params.id;

    const patientRef = useRef(),
          checkupRef = useRef(),
          deleteModalRef = useRef();

    const [{ isLoading, showLoading, patient, checkup, checkupData, checkupForm, checkupButton }, setState] = useState(
        {
            isLoading: true,
            showLoading: false,
            patient: '',
            checkup: '',
            checkupData: '',
            checkupForm: '',
            checkupButton: ''
        }
    )

    useEffect(() => {
        switch (mode) {
            case 'create':
                setState((state) => {
                    return { ...state, isLoading: false, patient: Object.assign({}, initialPatient), 
                             checkup: Object.assign({}, initialCheckup), checkupForm: false };
                });
                break;
            case 'display':
                PatientService.getPatient(id).then((patientRes) => {
                    let receivedPatient = patientRes.data;
                    receivedPatient.birthdate = new Date(receivedPatient.birthdate);

                    initialCheckup.patientUuid = receivedPatient.patientUuid;

                    CheckupService.getCheckupByPatientId(id).then((checkupRes) => {
                        let receivedCheckup = checkupRes.data,
                        newCheckupMode, 
                        newCheckup, 
                        newCheckupData, newCheckupForm, newCheckupButton;

                        if (receivedCheckup) {
                            receivedCheckup.checkupDate = new Date(receivedCheckup.checkupDate);
                            if (!receivedCheckup.healthFacility) receivedCheckup.healthFacility = '-';

                            newCheckup = receivedCheckup;
                            newCheckupData = true;
                            newCheckupForm = true;
                            newCheckupButton = false;
                        } else {
                            newCheckup = Object.assign({}, initialCheckup);
                            newCheckupData = false;
                            newCheckupForm = false;
                            newCheckupButton = true;
                        }

                        setState((state) => {
                            return { ...state, isLoading: false, patient: receivedPatient, checkupMode: newCheckupMode, checkup: newCheckup, 
                                     checkupData: newCheckupData, checkupForm: newCheckupForm, checkupButton: newCheckupButton };
                        });
                    });
                });
                break; 
            default:
        }

        setTimeout(() => {
            setState((state) => {
              return { ...state, showLoading: true };
            });
        }, 100);
    }, [mode, id]);
    
    function showCheckupForm() {
        setState((state) => {
            return { ...state, checkupForm: !checkupForm };
        });
    };
  
    function showCheckupButton() {
        setState((state) => {
            return { ...state, checkupButton: !checkupButton };
        });
    };

    function addPatient(patientId) {
        if (!checkupForm) {
            setState((state) => {
                return { ...state, patient: Object.assign({}, initialPatient) };
            });

            toast.success('Пациент успешно добавлен');
        } else {
            checkupRef.current.addCheckup(
                patientId, 
                () => {
                    setState((state) => {
                        return { ...state, patient: Object.assign({}, initialPatient), checkup: Object.assign({}, initialCheckup) };
                    });
    
                    toast.success('Пациент и обследование успешно добавлены');
                }, 
                () => {
                    patientRef.current.deletePatient(patientId);
                }
            );
        }
    };

    function updatePatient(patient) {
        setState((state) => {
            return { ...state, patient: patient };
        });

        toast.success('Пациент успешно обновлён');
    };

    function hideCheckup() {
        setState((state) => {
            return { ...state, checkup: Object.assign({}, initialCheckup), checkupForm: false };
        });
    };

    function addCheckup(checkup) {
        setState((state) => {
            return { ...state, checkup: checkup, checkupData: true };
        });

        toast.success('Обследование успешно добавлено');
    };

    function updateCheckup(checkup) {
        setState((state) => {
            return { ...state, checkup: checkup };
        });

        toast.success('Обследование успешно обновлено');
    };

    function deleteCheckup() {
        setState((state) => {
            return { ...state, checkup: Object.assign({}, initialCheckup), checkupData: false, checkupForm: false,
                     checkupButton: true };
        });
    
        hideDeleteModal();
    
        toast.success('Обследование успешно удалено');
    };

    function showDeleteModal() {
        let deleteModal = new Modal(deleteModalRef.current);
        deleteModal.show();
    };
  
    function hideDeleteModal() {
        let deleteModal = Modal.getInstance(deleteModalRef.current);
        deleteModal.hide();
    };

    function generateCheckup() {
        if (checkupData) 
            return <CheckupForm ref={checkupRef} mode={mode} checkup={checkup} updateCheckup={updateCheckup} deleteCheckup={showDeleteModal} />
        else
            return <>
                        { 
                            checkupButton && 
                            <button className={'btn ' + styles.btn__new_form} onClick={() => showCheckupForm()}>Добавить обследование</button> 
                        }
                        <CSSTransition
                            in={checkupForm}
                            timeout={ {enter: 400, exit: 200} }
                            classNames={{
                              enter: styles.animated_form__enter,
                              enterActive: styles.animated_form__enter_active,
                              exit: styles.animated_form__exit,
                              exitActive: styles.animated_form__exit_active
                            }}
                            unmountOnExit
                            onEnter={() => showCheckupButton()}
                            onExited={() => showCheckupButton()}
                            >
                            <CheckupForm ref={checkupRef} mode='display-create' checkup={checkup} hideCheckup={hideCheckup} addCheckup={addCheckup}/>
                        </CSSTransition>
                   </>
    }

    return (
        <>
            { 
                !isLoading &&
                <div className='container-fluid'>
                    <div className='row mt-5 mb-5'>
                        <div className='col d-flex justify-content-center'>
                            <PatientForm ref={patientRef} mode={mode} patient={patient}updatePatient={updatePatient} addPatient={addPatient} />
                        </div>
                    </div>
                    {
                        mode === 'create' &&
                        <div className='row mb-5'>
                                <div className='col d-flex gap-2 justify-content-center align-items-center'>
                                    <label className={styles.label__switch} htmlFor='checkupSwitch'>Добавить обследование:</label>
                                    <Switch
                                        checked={checkupForm}
                                        onChange={() => showCheckupForm()}
                                        onColor='#9e75c0'
                                        onHandleColor='#8763a3'
                                        handleDiameter={30}
                                        uncheckedIcon={false}
                                        checkedIcon={false}
                                        boxShadow='0px 1px 5px rgba(0, 0, 0, 0.6)'
                                        activeBoxShadow='0px 0px 1px 10px rgba(0, 0, 0, 0.2)'
                                        height={20}
                                        width={48}
                                        id='checkupSwitch'
                                        />
                                </div>
                        </div>
                    }
                    <div className='row'>
                        <div className='col d-flex justify-content-center'>
                            { 
                                mode === 'create' &&
                                    <CheckupForm ref={checkupRef} mode={mode} checkup={checkup} disabled={!checkupForm} />
                            }
                            {
                                mode === 'display' &&
                                <>
                                    { generateCheckup() }
                                    <DeleteModal ref={deleteModalRef} onAccept={deleteCheckup} onCancel={hideDeleteModal} />
                                </>
                            }
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
    );
}

export default DataForm;