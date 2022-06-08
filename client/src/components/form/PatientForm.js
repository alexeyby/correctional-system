import { useState, forwardRef, useImperativeHandle, useEffect } from 'react';
import { format } from 'date-fns';
import DateInput from '../input/DateInput';
import PatientService from '../../services/PatientService';
import styles from './PatientForm.module.css';

const sexes = {
    sexMale: 'MALE',
    sexFemale: 'FEMALE'
};

const historyNumberRegex = new RegExp('^[0-9]*$');

const defaultValidation = { value: false, label: '' };

const initialValidation = {
    historyNumber: Object.assign({}, defaultValidation),
    lastName: Object.assign({}, defaultValidation),
    firstName: Object.assign({}, defaultValidation),
    surname: Object.assign({}, defaultValidation),
    birthdate: Object.assign({}, defaultValidation),
    sex: Object.assign({}, defaultValidation),
    failure: Object.assign({}, defaultValidation)
};

const PatientForm = forwardRef((props, ref) => {
    const [{ mode, patient, draftPatient, validation }, setState] = useState(
            {
                mode: '',
                patient: {
                    patientUuid: '',
                    historyNumber: '',
                    lastName: '',
                    firstName: '',
                    surname: '',
                    birthdate: '',
                    sex: '',
                    failure: '',
                    hasCheckup: ''
                },
                draftPatient: '',
                validation: Object.assign({}, initialValidation)
            }          
          );

    useEffect(() => {
        setState((state) => {
            return { ...state, mode: props.mode, patient: Object.assign({}, props.patient), draftPatient: '' };
        })
    }, [props.mode, props.patient]);

    useImperativeHandle(ref, () => ({
        deletePatient(patientId) {
            PatientService.deletePatient(patientId);
        }
    }));

    function changeHistoryNumberHandler(event) {
        let historyNumber = event.target.value;

        if (historyNumberRegex.test(historyNumber))
            if (validation.historyNumber)
                setState((state) => {
                    return { ...state, patient: { ...state.patient, historyNumber: historyNumber },
                             validation: { ...state.validation, historyNumber: Object.assign({}, defaultValidation) } };
                });
            else
                setState((state) => {
                    return { ...state, patient: { ...state.patient, historyNumber: historyNumber } };
                }); 
    };

    function changeLastNameHandler(event) {
        let lastName = event.target.value;

        if (validation.lastName)
            setState((state) => {
                return { ...state, patient: { ...state.patient, lastName: lastName }, 
                         validation: { ...state.validation, lastName: Object.assign({}, defaultValidation) } };
            });
        else
            setState((state) => {
                return { ...state, patient: { ...state.patient, lastName: lastName } };
            }); 
    };

    function changeFirstNameHandler(event) {
        let firstName = event.target.value;

        if (validation.firstName)
            setState((state) => {
                return { ...state, patient: { ...state.patient, firstName: firstName }, 
                         validation: { ...state.validation, firstName: Object.assign({}, defaultValidation) } };
            });
        else
            setState((state) => {
                return { ...state, patient: { ...state.patient, firstName: firstName } };
            }); 
    };

    function changeSurnameHandler(event) {
        let surname = event.target.value;

        if (validation.surname)
            setState((state) => {
                return { ...state, patient: { ...state.patient, surname: surname }, 
                         validation: { ...state.validation, surname: Object.assign({}, defaultValidation) } };
            });
        else
            setState((state) => {
                return { ...state, patient: { ...state.patient, surname: surname } };
            }); 
    };

    function changeBirthdateHandler(event) {
        let birthdate = event;

        if (validation.birthdate)
            setState((state) => {
                return { ...state, patient: { ...state.patient, birthdate: birthdate },
                         validation: { ...state.validation, birthdate: Object.assign({}, defaultValidation) } };
            });
        else
            setState((state) => {
                return { ...state, patient: { ...state.patient, birthdate: birthdate } };
            });
    };

    function changeSexHandler(event) {
        let id = event.target.getAttribute('id'),
        sex = sexes[id];

        if (validation.sex)
            setState((state) => {
                return { ...state, patient: { ...state.patient, sex: sex },
                         validation: { ...state.validation, sex: Object.assign({}, defaultValidation) } };
            });
        else
            setState((state) => {
                return { ...state, patient: { ...state.patient, sex: sex } };
            });
    };

    function changeFailureHandler() {
        patient.failure = !patient.failure;
        setState((state) => {
            return { ...state, patient: patient };
        });
    };

    function addPatient() {
        let validationResult = validateBlank();

        if (!validationResult) {
            let newPatient = Object.assign({}, patient);

            newPatient.birthdate = format(newPatient.birthdate, 'yyyy-MM-dd');

            PatientService.postPatient(newPatient).then((res) => {
                let patientId = res.data.patientUuid;
    
                props.addPatient(patientId);
            }, 
            (error) => {
                if (error.response.status === 409) {
                    setState((state) => {
                        return { ...state, validation: { ...state.validation, 
                                 historyNumber: { value: true, label: '№ уже используется' } } };
                    });
                }
            });
        }
    };

    function updatePatient() {
        let validationResult = validateBlank();

        if (!validationResult) {
            let changedPatient = Object.assign({}, patient);
        
            changedPatient.birthdate = format(changedPatient.birthdate, 'yyyy-MM-dd');
    
            PatientService.putPatient(changedPatient).then((res) => {
                changedPatient = res.data;
                changedPatient.birthdate = new Date(changedPatient.birthdate);
    
                props.updatePatient(changedPatient);
            },
            (error) => {
                if (error.response.status === 409) {
                    setState((state) => {
                        return { ...state, validation: { ...state.validation, 
                                 historyNumber: { value: true, label: '№ уже используется' } } };
                    });
                }
            });
        }
    };

    function changeEditMode() {
        if (mode === 'edit') {
            Object.assign(patient, draftPatient);

            setState((state) => {
                return { ...state,  patient: patient, draftPatient: '', mode: 'display', 
                         validation: Object.assign({}, initialValidation) };
            })
        } else {
            setState((state) => {
                return { ...state,  draftPatient: Object.assign({}, patient), mode: 'edit' };
            });
        }
    };

    function validateBlank() {
        let newValidation = Object.assign({}, validation),
        error = false;

        if (!patient.historyNumber) {
            newValidation.historyNumber.value = true;
            newValidation.historyNumber.label = 'Заполните поле';
            error = true;
        }
        if (!patient.lastName) {
            newValidation.lastName.value = true;
            newValidation.lastName.label = 'Заполните поле';
            error = true;
        }
        if (!patient.firstName) {
            newValidation.firstName.value = true;
            newValidation.firstName.label = 'Заполните поле';
            error = true;
        }
        if (!patient.surname) {
            newValidation.surname.value = true;
            newValidation.surname.label = 'Заполните поле';
            error = true;
        }
        if (!patient.birthdate) {
            newValidation.birthdate.value = true;
            newValidation.birthdate.label = 'Заполните поле';
            error = true;
        }
        if (!patient.sex) {
            newValidation.sex.value = true;
            newValidation.sex.label = 'Выберите один из вариантов';
            error = true;
        }
       
        if (error) setState((state) => { return { ...state, validation: newValidation } });

        return error;
    };

    function generateButtons() {
        switch (mode) {
            case 'create':
                return <button className={'btn ' + styles.btn__form} onClick={addPatient}>Добавить</button>
            case 'edit':
                return <>
                            <button className={'btn ' + styles.btn__form} onClick={updatePatient}>Сохранить</button>
                            <button className={'btn ' + styles.btn__form} onClick={changeEditMode}>Отмена</button> 
                       </>
            case 'display':
                return <button className={'btn ' + styles.btn__form} onClick={changeEditMode}>Изменить</button>
            default:
        }
    };

    return (
        <div className={'card ' + styles.card__form}>
            <div className={'card-header ' + styles.card_header__form}>Пациент</div>
            <div className='card-body'>
                <div className='card'>
                    <div className='card-body'>
                        <div>
                            <label 
                                className={'form-label ' + styles.form_label__position} 
                                htmlFor='historyNumber'>История болезни №:</label>
                            <input 
                                placeholder='0123456789' 
                                id='historyNumber' 
                                className={'form-control ' + styles.input__form + 
                                (validation.historyNumber.value ? ' is-invalid' : '')}
                                value={patient.historyNumber} 
                                onChange={(event) => changeHistoryNumberHandler(event)}
                                disabled={mode === 'display'} />
                            <div className='invalid-feedback'>
                                {validation.historyNumber.label}
                            </div>
                        </div>
                    </div>
                </div>
                <div className='card'>
                    <div className='card-body'>
                        <fieldset disabled={mode === 'display'}>
                            <div>
                                <label 
                                    className={'form-label ' + styles.form_label__position} 
                                    htmlFor='lastName'>Фамилия:</label>
                                <input 
                                    placeholder='Иванов' 
                                    id='lastName' 
                                    className={'form-control ' + styles.input__form +
                                    (validation.lastName.value ? ' is-invalid' : '')}
                                    value={patient.lastName} 
                                    onChange={(event) => changeLastNameHandler(event)} />
                                <div className='invalid-feedback'>
                                    {validation.lastName.label}
                                </div>
                            </div>
                            <div>
                                <label 
                                    className={'form-label ' + styles.form_label__position} 
                                    htmlFor='firstName'>Имя:</label>
                                <input 
                                    placeholder='Иван' 
                                    id='firstName' 
                                    className={'form-control ' + styles.input__form +
                                    (validation.firstName.value ? ' is-invalid' : '')}
                                    value={patient.firstName} 
                                    onChange={(event) => changeFirstNameHandler(event)} />
                                <div className='invalid-feedback'>
                                    {validation.firstName.label}
                                </div>
                            </div>
                            <div>
                                <label className={'form-label ' + styles.form_label__position} 
                                    htmlFor='surname'>Отчество:</label>
                                <input placeholder='Иванович' id='surname' 
                                    className={'form-control ' + styles.input__form +
                                    (validation.surname.value ? ' is-invalid' : '')}
                                    value={patient.surname} 
                                    onChange={(event) => changeSurnameHandler(event)} />
                                <div className='invalid-feedback'>
                                    {validation.surname.label}
                                </div>
                            </div>
                            <div>
                                <label 
                                    className={'form-label ' + styles.form_label__position} 
                                    htmlFor='birthdate'>Дата рождения:</label>
                                <div id='birthdate' className={(validation.birthdate.value ? ' is-invalid' : '')}>
                                    <DateInput 
                                        mode='birthdate'
                                        disabled={mode === 'display'}
                                        isValid={validation.birthdate.value} 
                                        selected={patient.birthdate} 
                                        onChange={(event) => changeBirthdateHandler(event)} />
                                </div>
                                <div className='invalid-feedback'>
                                    {validation.birthdate.label}
                                </div>
                            </div>
                            <div>
                                <label className={'form-label ' + styles.form_label__position} htmlFor='sex'>Пол:</label>
                                <div className={styles.radio_group + (validation.sex.value ? ' is-invalid' : '')} id='sex'>
                                    <div className='form-check form-check-inline'>
                                         <input 
                                            className={'form-check-input ' + styles.radio_field + 
                                            (validation.sex.value ? ' is-invalid' : '')} 
                                            type='radio' 
                                            name='sex' value={patient.sex} 
                                            id='sexMale' 
                                            onChange={(event) => changeSexHandler(event)} 
                                            checked={patient.sex === 'MALE'} />
                                        <label 
                                            className={'form-check-label ' + styles.radio_label} 
                                            htmlFor='sexMale'>мужской</label>
                                    </div>
                                    <div className='form-check form-check-inline'>
                                        <input 
                                            className={'form-check-input ' + styles.radio_field + 
                                            (validation.sex.value ? ' is-invalid' : '')} 
                                            type='radio' 
                                            name='sex' 
                                            value={patient.sex} 
                                            id='sexFemale' 
                                            onChange={(event) => changeSexHandler(event)} 
                                            checked={patient.sex === 'FEMALE'} />
                                        <label 
                                            className={'form-check-label ' + styles.radio_label} 
                                            htmlFor='sexFemale'>женский</label>
                                    </div>
                                </div>
                                <div className='invalid-feedback'>
                                    {validation.sex.label}
                                </div>
                            </div>
                            <div>
                                <label 
                                    className={'form-label ' + styles.form_label__position} 
                                    htmlFor='failure'>Почечная и (или) печёночная недостаточность:</label>
                                <div className={styles.radio_group} id='failure'>
                                   <input 
                                        className={'form-check-input ' + styles.radio_field}
                                        type='checkbox' 
                                        value={patient.failure} 
                                        onChange={(event) => changeFailureHandler(event)}
                                        checked={patient.failure} />
                                </div>
                            </div>
                        </fieldset>
                    </div>
                </div>
            </div>
            <div className='card-footer'>
                <div className='d-flex gap-3 mt-2 mb-2 flex-wrap'>
                    { generateButtons() }
                </div>
            </div>
        </div>
    );
});

export default PatientForm;