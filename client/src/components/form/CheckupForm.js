import { useState, forwardRef, useImperativeHandle, useEffect } from 'react';
import { format } from 'date-fns';
import DateInput from '../input/DateInput';
import CheckupService from '../../services/CheckupService';
import styles from './CheckupForm.module.css';

const mobilities = {
    bedrest: {
        value: 'BEDREST',
        label: 'Постельный режим'
    },
    ambulation: {
        value: 'AMBULATION',
        label: 'Палатный режим'
    },
    noRestriction: {
        value: 'NO_RESTRICTION',
        label: 'Общий режим'
    }
};

const temperatures = {
    normal: {
        value: 'NORMAL',
        label: 'Менее 38°С'
    },
    slightlyHigh: {
        value: 'SLIGHTLY_HIGH',
        label: '38°С - 38,9°'
    },
    high: {
        value: 'HIGH',
        label: '39°С - 39,9°С'
    },
    veryHigh: {
        value: 'VERY_HIGH',
        label: '40°С - 40,9°С'
    },
    extremelyHigh: {
        value: 'EXTREMELY_HIGH',
        label: '41°С и выше'
    }
};

const injuries = {
    noInjuries: {
        value: 'NO_INJURIES',
        label: 'Пациент без осложнений',
        group: 1
    },
    afterSurgery: {
        value: 'AFTER_SURGERY',
        label: 'После операции',
        group: 1
    },
    boneFracture: {
        value: 'BONE_FRACTURE',
        label: 'Перелом',
        group: 1
    },
    sepsis: {
        value: 'SEPSIS',
        label: 'Сепсис',
        group: 1
    },
    peritonitis: {
        value: 'PERITONITIS',
        label: 'Перитонит',
        group: 1
    },
    polytraumaRehabilitation: {
        value: 'POLYTRAUMA_REHABILITATION',
        label: 'Политравма, реабилитация',
        group: 2
    },
    polytraumaSepsis: {
        value: 'POLYTRAUMA_SEPSIS',
        label: 'Политравма и сепсис',
        group: 2
    },
    moderateBurns: {
        value: 'MODERATE_BURNS',
        label: 'Ожоги 30 - 50%',
        group: 2
    },
    severeBurns: {
        value: 'SEVERE_BURNS',
        label: 'Ожоги 50 - 70%',
        group: 2
    },
    verySevereBurns: {
        value: 'VERY_SEVERE_BURNS',
        label: 'Ожоги 70 - 90%',
        group: 2
    }
};

const numberRegex = new RegExp('(^[0-9]+[.]?[0-9]*$)|(^$)');

const defaultValidation = { value: false, label: '' };

const initialValidation = {
    weight: Object.assign({}, defaultValidation),
    height: Object.assign({}, defaultValidation),
    skinfoldThickness: Object.assign({}, defaultValidation),
    armCircumference: Object.assign({}, defaultValidation),
    albumin: Object.assign({}, defaultValidation),
    transferrin: Object.assign({}, defaultValidation),
    lymphocyte: Object.assign({}, defaultValidation),
    skinTest: Object.assign({}, defaultValidation),
    mobility: Object.assign({}, defaultValidation),
    temperature: Object.assign({}, defaultValidation),
    injury: Object.assign({}, defaultValidation),
    checkupDate: Object.assign({}, defaultValidation)
};

const CheckupForm = forwardRef((props, ref) => {
    const disabled = props.disabled;
    
    const [{ mode, checkup, draftCheckup, validation }, setState] = useState(
        {
            mode: '',
            checkup: {
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
                checkupDate: '',
                healthFacility: '',
                patientUuid: ''
            },
            draftCheckup: '',
            validation: Object.assign({}, initialValidation)
        }
    );

    useEffect(() => {
        setState((state) => {
            return { ...state, mode: props.mode, checkup: Object.assign({}, props.checkup), draftCheckup: '' };
        })
    }, [props.mode, props.checkup]);

    useImperativeHandle(ref, () => ({
        addCheckup(patientId, resolve, reject) {
            let validationResult = validateBlank();

            if (!validationResult) {
                let newCheckup = Object.assign({}, checkup);

                newCheckup.patientUuid = patientId;
                newCheckup.checkupDate = format(checkup.checkupDate, 'yyyy-MM-dd');
    
                CheckupService.postCheckup(newCheckup).then(() => {
                    resolve();
                },
                () => {
                    reject();
                });
            } else {
                reject();
            }
        }
      }));

    function changeWeightHandler(event) {
        let weight = event.target.value;

        if (numberRegex.test(weight))
            if (validation.weight)
                setState((state) => {
                    return { ...state, checkup: { ...state.checkup, weight: weight }, 
                             validation: { ...state.validation, weight: Object.assign({}, defaultValidation) } };
                });
            else
                setState((state) => {
                    return { ...state, checkup: { ...state.checkup, weight: weight } };
                });
    }

    function changeHeightHandler(event) {
        let height = event.target.value;

        if (numberRegex.test(height))
            if (validation.height)
                setState((state) => {
                    return { ...state, checkup: { ...state.checkup, height: height }, 
                             validation: { ...state.validation, height: Object.assign({}, defaultValidation) } };
                });
            else
                setState((state) => {
                    return { ...state, checkup: { ...state.checkup, height: height } };
                });
    }

    function changeSkinfoldThicknessHandler(event) {
        let skinfoldThickness = event.target.value;

        if (numberRegex.test(skinfoldThickness))
            if (validation.skinfoldThickness)
                setState((state) => {
                    return { ...state, checkup: { ...state.checkup, skinfoldThickness: skinfoldThickness }, 
                             validation: { ...state.validation, skinfoldThickness: Object.assign({}, defaultValidation) } };
                });
            else
                setState((state) => {
                    return { ...state, checkup: { ...state.checkup, skinfoldThickness: skinfoldThickness } };
                });
    }

    function changeArmCircumferenceHandler(event) {
        let armCircumference = event.target.value;

        if (numberRegex.test(armCircumference))
            if (validation.armCircumference)
                setState((state) => {
                    return { ...state, checkup: { ...state.checkup, armCircumference: armCircumference }, 
                             validation: { ...state.validation, armCircumference: Object.assign({}, defaultValidation) } };
                });
            else
                setState((state) => {
                    return { ...state, checkup: { ...state.checkup, armCircumference: armCircumference } };
                });
    }

    function changeAlbuminHandler(event) {
        let albumin = event.target.value;

        if (numberRegex.test(albumin))
            if (validation.albumin)
                setState((state) => {
                    return { ...state, checkup: { ...state.checkup, albumin: albumin }, 
                             validation: { ...state.validation, albumin: Object.assign({}, defaultValidation) } };
                });
            else
                setState((state) => {
                    return { ...state, checkup: { ...state.checkup, albumin: albumin } };
                });
    }

    function changeTransferrinHandler(event) {
        let transferrin = event.target.value;

        if (numberRegex.test(transferrin))
            if (validation.transferrin)
                setState((state) => {
                    return { ...state, checkup: { ...state.checkup, transferrin: transferrin }, 
                             validation: { ...state.validation, transferrin: Object.assign({}, defaultValidation) } };
                });
            else
                setState((state) => {
                    return { ...state, checkup: { ...state.checkup, transferrin: transferrin } };
                });
    }

    function changeLymphocyteHandler(event) {
        let lymphocyte = event.target.value;

        if (numberRegex.test(lymphocyte))
            if (validation.lymphocyte)
                setState((state) => {
                    return { ...state, checkup: { ...state.checkup, lymphocyte: lymphocyte }, 
                             validation: { ...state.validation, lymphocyte: Object.assign({}, defaultValidation) } };
                });
            else
                setState((state) => {
                    return { ...state, checkup: { ...state.checkup, lymphocyte: lymphocyte } };
                });
    }

    function changeSkinTestHandler(event) {
        let skinTest = event.target.value;

        if (numberRegex.test(skinTest))
            if (validation.skinTest)
                setState((state) => {
                    return { ...state, checkup: { ...state.checkup, skinTest: skinTest }, 
                             validation: { ...state.validation, skinTest: Object.assign({}, defaultValidation) } };
                });
            else
                setState((state) => {
                    return { ...state, checkup: { ...state.checkup, skinTest: skinTest } };
                });
    }

    function changeMobilityHandler(event) {
        let id = event.target.getAttribute('id'),
        mobility = mobilities[id].value;

        if (validation.mobility)
            setState((state) => {
                return { ...state, checkup: { ...state.checkup, mobility: mobility },
                         validation: { ...state.validation, mobility: Object.assign({}, defaultValidation) } };
            });
        else
            setState((state) => {
                return { ...state, checkup: { ...state.checkup, mobility: mobility } };
            });
    }

    function changeTemperatureHandler(event) {
        let id = event.target.getAttribute('id'),
        temperature = temperatures[id].value;

        if (validation.temperature)
            setState((state) => {
                return { ...state, checkup: { ...state.checkup, temperature: temperature },
                         validation: { ...state.validation, temperature: Object.assign({}, defaultValidation) } };
            });
        else
            setState((state) => {
                return { ...state, checkup: { ...state.checkup, temperature: temperature } };
            });
    }

    function changeInjuryHandler(event) {
        let id = event.target.getAttribute('id'),
        injury = injuries[id].value;

        if (validation.injury)
            setState((state) => {
                return { ...state, checkup: { ...state.checkup, injury: injury },
                         validation: { ...state.validation, injury: Object.assign({}, defaultValidation) } };
            });
        else
            setState((state) => {
                return { ...state, checkup: { ...state.checkup, injury: injury } };
            });
    }

    function changeCheckupDateHandler(event) {
        let checkupDate = event;

        if (validation.checkupDate)
            setState((state) => {
                return { ...state, checkup: { ...state.checkup, checkupDate: checkupDate },
                         validation: { ...state.validation, checkupDate: Object.assign({}, defaultValidation) } };
            });
        else
            setState((state) => {
                return { ...state, checkup: { ...state.checkup, checkupDate: checkupDate } };
            });
    }

    function changeHealthFacilityHandler(event) {
        let healthFacility = event.target.value;

        setState((state) => {
            return { ...state, checkup: { ...state.checkup, healthFacility: healthFacility } };
        });
    }

    function addEditCheckup() {
        let validationResult = validateBlank();

        if (!validationResult) {
            let newCheckup = Object.assign({}, checkup);

            newCheckup.checkupDate = format(newCheckup.checkupDate, 'yyyy-MM-dd');
    
            CheckupService.postCheckup(newCheckup).then((res) => {
                newCheckup = res.data;
                newCheckup.checkupDate = new Date(newCheckup.checkupDate);
                if(!newCheckup.healthFacility) newCheckup.healthFacility = '-';
                
                props.addCheckup(newCheckup);
            });
        }
    };

    function updateCheckup() {
        let validationResult = validateBlank();

        if (!validationResult) {
            let changedCheckup = Object.assign({}, checkup);

            changedCheckup.checkupDate = format(changedCheckup.checkupDate, 'yyyy-MM-dd');
    
            CheckupService.putCheckup(changedCheckup).then((res) => {
                changedCheckup = res.data;
                changedCheckup.checkupDate = new Date(changedCheckup.checkupDate);
                if (!changedCheckup.healthFacility) changedCheckup.healthFacility = '-';
    
                props.updateCheckup(changedCheckup);
            });
        }
    };

    function deleteCheckup() {
        CheckupService.deleteCheckup(checkup.checkupUuid).then(() => {
            props.deleteCheckup();
        });
    };

    function changeEditMode() {
        if (mode === 'edit') {
            let formattedDraftCheckup = Object.assign({}, draftCheckup);
            if (!formattedDraftCheckup.healthFacility) formattedDraftCheckup.healthFacility = '-';

            setState((state) => {
                return { ...state, checkup: formattedDraftCheckup, draftCheckup: '', mode: 'display', 
                         validation: Object.assign({}, initialValidation) };
            });
        } else {           
            let healthFacility = checkup.healthFacility !== '-' ? checkup.healthFacility : '';

            setState((state) => {
                return { ...state, checkup: { ...state.checkup, healthFacility: healthFacility }, 
                         draftCheckup: Object.assign({}, checkup), mode: 'edit' };
            });
        }
    };

    function validateBlank() {
        let newValidation = Object.assign({}, validation),
        error = false;

        if (!checkup.weight) {
            newValidation.weight.value = true;
            newValidation.weight.label = 'Заполните поле';
            error = true;
        } else {
            let weight = Number(checkup.weight);

            if (isNaN(weight) || weight === Infinity) {
                newValidation.weight.value = true;
                newValidation.weight.label = 'Введите допустимое значение';
                error = true;
            }
        }
        if (!checkup.height) {
            newValidation.height.value = true;
            newValidation.height.label = 'Заполните поле';
            error = true;
        } else {
            let height = Number(checkup.height);

            if (isNaN(height) || height === Infinity) {
                newValidation.height.value = true;
                newValidation.height.label = 'Введите допустимое значение';
                error = true;
            }
        }
        if (!checkup.skinfoldThickness) {
            newValidation.skinfoldThickness.value = true;
            newValidation.skinfoldThickness.label = 'Заполните поле';
            error = true;
        } else {
            let height = Number(checkup.height);

            if (isNaN(height) || height === Infinity) {
                newValidation.skinfoldThickness.value = true;
                newValidation.skinfoldThickness.label = 'Введите допустимое значение';
                error = true;
            }
        }
        if (!checkup.armCircumference) {
            newValidation.armCircumference.value = true;
            newValidation.armCircumference.label = 'Заполните поле';
            error = true;
        } else {
            let armCircumference = Number(checkup.armCircumference);

            if (isNaN(armCircumference) || armCircumference === Infinity) {
                newValidation.armCircumference.value = true;
                newValidation.armCircumference.label = 'Введите допустимое значение';
                error = true;
            }
        }
        if (!checkup.albumin) {
            newValidation.albumin.value = true;
            newValidation.albumin.label = 'Заполните поле';
            error = true;
        } else {
            let albumin = Number(checkup.albumin);

            if (isNaN(albumin) || albumin === Infinity) {
                newValidation.albumin.value = true;
                newValidation.albumin.label = 'Введите допустимое значение';
                error = true;
            }
        }
        if (!checkup.transferrin) {
            newValidation.transferrin.value = true;
            newValidation.transferrin.label = 'Заполните поле';
            error = true;
        } else {
            let transferrin = Number(checkup.transferrin);

            if (isNaN(transferrin) || transferrin === Infinity) {
                newValidation.transferrin.value = true;
                newValidation.transferrin.label = 'Введите допустимое значение';
                error = true;
            }
        }
        if (!checkup.lymphocyte) {
            newValidation.lymphocyte.value = true;
            newValidation.lymphocyte.label = 'Заполните поле';
            error = true;
        } else {
            let lymphocyte = Number(checkup.lymphocyte);

            if (isNaN(lymphocyte) || lymphocyte === Infinity) {
                newValidation.lymphocyte.value = true;
                newValidation.lymphocyte.label = 'Введите допустимое значение';
                error = true;
            }
        }
        if (!checkup.skinTest) {
            newValidation.skinTest.value = true;
            newValidation.skinTest.label = 'Заполните поле';
            error = true;
        } else {
            let skinTest = Number(checkup.skinTest);

            if (isNaN(skinTest) || skinTest === Infinity) {
                newValidation.skinTest.value = true;
                newValidation.skinTest.label = 'Введите допустимое значение';
                error = true;
            }
        }
        if (!checkup.mobility) {
            newValidation.mobility.value = true;
            newValidation.mobility.label = 'Выберите один из вариантов';
            error = true;
        }
        if (!checkup.temperature) {
            newValidation.temperature.value = true;
            newValidation.temperature.label = 'Выберите один из вариантов';
            error = true;
        }
        if (!checkup.injury) {
            newValidation.injury.value = true;
            newValidation.injury.label = 'Выберите один из вариантов';
            error = true;
        }
        if (!checkup.checkupDate) {
            newValidation.checkupDate.value = true;
            newValidation.checkupDate.label = 'Заполните поле';
            error = true;
        }
       
        if (error) setState((state) => { return { ...state, validation: newValidation } });

        return error;
    };

    function generateMobilityFieldset() {
        return  <>
                    <fieldset className={(validation.mobility.value ? ' is-invalid' : '')}
                        disabled={mode === 'display' || disabled}>
                        {   
                            Object.keys(mobilities).map((id) => 
                                <div className='form-check' key={id}>
                                    <input 
                                        className={'form-check-input ' + styles.radio_field + 
                                        (validation.mobility.value ? ' is-invalid' : '')} 
                                        type='radio' name='mobility' value={checkup.mobility} 
                                        id={id} onChange={(event) => changeMobilityHandler(event)} 
                                        checked={checkup.mobility === mobilities[id].value} 
                                        />
                                    <label 
                                        className={'form-check-label ' + styles.radio_label} 
                                        htmlFor={id}>{mobilities[id].label}</label>
                                </div>
                            )
                        }
                    </fieldset>
                    <div className='invalid-feedback'>
                        {validation.mobility.label}
                    </div>
                </>
    };

    function generateTemperatureFieldset() {
        return  <>
                    <fieldset className={(validation.temperature.value ? ' is-invalid' : '')}
                        disabled={mode === 'display' || disabled}>
                        {   
                            Object.keys(temperatures).map((id) => 
                                <div className='form-check' key={id}>
                                    <input 
                                        className={'form-check-input ' + styles.radio_field + 
                                        (validation.temperature.value ? ' is-invalid' : '')}  
                                        type='radio' name='temperature' value={checkup.temperature} 
                                        id={id} onChange={(event) => changeTemperatureHandler(event)} 
                                        checked={checkup.temperature === temperatures[id].value} 
                                        />
                                    <label 
                                        className={'form-check-label ' + styles.radio_label} 
                                        htmlFor={id}>{temperatures[id].label}</label>
                                </div>
                            )
                        }
                    </fieldset>
                    <div className='invalid-feedback'>
                        {validation.temperature.label}
                    </div>
                </>
    };

    function generateInjuryFieldset() {
        return  <>
                    <fieldset className={(validation.injury.value ? ' is-invalid' : '')}
                        disabled={mode === 'display' || disabled}>
                        <div className='row'>
                            <div className='col'> 
                                { Object.keys(injuries).map((id) => 
                                    injuries[id].group === 1 && generateInjuryField(id, injuries[id].value, injuries[id].label)) }
                            </div>
                            <div className='col'>
                                { Object.keys(injuries).map((id) => 
                                    injuries[id].group === 2 && generateInjuryField(id, injuries[id].value, injuries[id].label)) }
                            </div>
                        </div>
                    </fieldset>
                    <div className='invalid-feedback'>
                        {validation.injury.label}
                    </div>
                </>
    };

    function generateInjuryField(id, value, label) {
        return  <div className='form-check' key={id}>
                    <input className={'form-check-input ' + styles.radio_field + 
                        (validation.injury.value ? ' is-invalid' : '')}
                        type='radio' name='injury' value={checkup.injury} 
                        id={id} onChange={(event) => changeInjuryHandler(event)} checked={checkup.injury === value} />
                    <label className={'form-check-label ' + styles.radio_label} htmlFor={id}>{label}</label>
                </div>
    };

    function generateButtons() {
        switch (mode) {
            case 'display-create':
                return  <>
                            <button className={'btn ' + styles.btn__form} onClick={addEditCheckup}>Добавить</button>
                            <button className={'btn ' + styles.btn__form} onClick={props.hideCheckup}>Отмена</button> 
                        </>
            case 'edit':
                return  <>
                            <button className={'btn ' + styles.btn__form} onClick={updateCheckup}>Сохранить</button>
                            <button className={'btn ' + styles.btn__form} onClick={changeEditMode}>Отмена</button> 
                        </>
            case 'display':
                return  <>
                            <button className={'btn ' + styles.btn__form} onClick={changeEditMode}>Изменить</button>
                            <button className={'btn ' + styles.btn__form} onClick={deleteCheckup}>Удалить</button>
                        </>
            default:
        }
    };

    return (
        <div className={'card ' + styles.card__main + (mode === 'create' && disabled ? ' ' + styles.form__disabled : '')}>
            <div className={'card-header ' + styles.card_header__main}>Обследование</div>
            <div className='card-body'>
                <div className='row mb-2'>
                    <div className='col d-flex'>
                        <div className={'card flex-grow-1 ' + styles.card__form}>
                            <div className={'card-header ' + styles.card_header__form}>Питательный статус пациента</div>
                            <div className='card-body d-flex flex-column'>
                                <div className={'card flex-grow-1 ' + styles.card__subform}>
                                    <div className='card-body'>
                                        <div className={'card-title ' + styles.card_title__division}>Антропометрические данные</div>
                                        <fieldset disabled={mode === 'display' || disabled}>
                                            <div className='row align-items-center'>
                                                <div className='col'>
                                                    <label 
                                                        className={'form-label ' + styles.form_label__position} 
                                                        htmlFor='weight'>Вес (кг):</label>
                                                    <input 
                                                        placeholder='75' 
                                                        id='weight' 
                                                        className={'form-control ' + styles.input__form +
                                                        (validation.weight.value ? ' is-invalid' : '')}
                                                        value={checkup.weight} 
                                                        onChange={(event) => changeWeightHandler(event)} />
                                                    <div className='invalid-feedback'>
                                                        {validation.weight.label}
                                                    </div>
                                                </div>
                                                <div className='col'>
                                                    <label 
                                                        className={'form-label ' + styles.form_label__position} 
                                                        htmlFor='skinfoldThickness'>Толщина КЖСТ (мм):</label>
                                                    <input 
                                                        placeholder='10' 
                                                        id='skinfoldThickness' 
                                                        className={'form-control ' + styles.input__form +
                                                        (validation.skinfoldThickness.value ? ' is-invalid' : '')}
                                                        value={checkup.skinfoldThickness} 
                                                        onChange={(event) => changeSkinfoldThicknessHandler(event)} />
                                                    <div className='invalid-feedback'>
                                                        {validation.skinfoldThickness.label}
                                                    </div>
                                                </div>
                                            </div>
                                            <div className='row align-items-center'>
                                                <div className='col'>
                                                    <label 
                                                        className={'form-label ' + styles.form_label__position} 
                                                        htmlFor='height'>Рост (см):</label>
                                                    <input 
                                                        placeholder='170' 
                                                        id='height' 
                                                        className={'form-control ' + styles.input__form +
                                                        (validation.height.value ? ' is-invalid' : '')}
                                                        value={checkup.height} onChange={(event) =>  changeHeightHandler(event)} />
                                                    <div className='invalid-feedback'>
                                                        {validation.height.label}
                                                    </div>
                                                </div>
                                                <div className='col'>
                                                    <label 
                                                        className={'form-label ' + styles.form_label__position} 
                                                        htmlFor='armCircumference'>Окружность плеча (см):</label>
                                                    <input 
                                                        placeholder='23' 
                                                        id='armCircumference' 
                                                        className={'form-control ' + styles.input__form +
                                                        (validation.armCircumference.value ? ' is-invalid' : '')}
                                                        value={checkup.armCircumference} 
                                                        onChange={(event) =>  changeArmCircumferenceHandler(event)} />
                                                    <div className='invalid-feedback'>
                                                        {validation.armCircumference.label}
                                                    </div>
                                                </div>
                                            </div>
                                        </fieldset>
                                    </div>
                                </div>
                                <div className={'card flex-grow-1 ' + styles.card__subform}>
                                    <div className='card-body'>
                                        <div className={'card-title ' + styles.card_title__division}>Лабораторные данные</div>
                                        <fieldset disabled={mode === 'display' || disabled}>
                                            <div className='row align-items-center'>
                                                <div className='col'>
                                                    <label 
                                                        className={'form-label ' + styles.form_label__position} 
                                                        htmlFor='albumin'>Альбумин (г/л):</label>
                                                    <input 
                                                        placeholder='35' 
                                                        id='albumin' 
                                                        className={'form-control ' + styles.input__form +
                                                        (validation.albumin.value ? ' is-invalid' : '')}
                                                        value={checkup.albumin} 
                                                        onChange={(event) =>  changeAlbuminHandler(event)} />
                                                    <div className='invalid-feedback'>
                                                        {validation.albumin.label}
                                                    </div>
                                                </div>
                                                <div className='col'>
                                                    <label 
                                                        className={'form-label ' + styles.form_label__position} 
                                                        htmlFor='skinfoldThickness'>Лимфоциты (10^9/л):</label>
                                                    <input 
                                                        placeholder='2' 
                                                        id='lymphocyte' 
                                                        className={'form-control ' + styles.input__form +
                                                        (validation.lymphocyte.value ? ' is-invalid' : '')}
                                                        value={checkup.lymphocyte} 
                                                        onChange={(event) =>  changeLymphocyteHandler(event)} />
                                                    <div className='invalid-feedback'>
                                                        {validation.lymphocyte.label}
                                                    </div>
                                                </div>
                                            </div>
                                            <div className='row align-items-center'>
                                                <div className='col'>
                                                    <label 
                                                        className={'form-label ' + styles.form_label__position} 
                                                        htmlFor='transferrin'>Трансферин (г/л):</label>
                                                    <input 
                                                        placeholder='2' 
                                                        id='transferrin' 
                                                        className={'form-control ' + styles.input__form +
                                                        (validation.transferrin.value ? ' is-invalid' : '')}
                                                        value={checkup.transferrin} 
                                                        onChange={(event) => changeTransferrinHandler(event)} />
                                                    <div className='invalid-feedback'>
                                                        {validation.transferrin.label}
                                                    </div>
                                                </div>
                                                <div className='col'>
                                                    <label 
                                                        className={'form-label ' + styles.form_label__position} 
                                                        htmlFor='armCircumference'>Кожная реакция (мм):</label>
                                                    <input 
                                                        placeholder='5' 
                                                        id='skinTest' 
                                                        className={'form-control ' + styles.input__form +
                                                        (validation.skinTest.value ? ' is-invalid' : '')}
                                                        value={checkup.skinTest} 
                                                        onChange={(event) => changeSkinTestHandler(event)} />
                                                    <div className='invalid-feedback'>
                                                        {validation.skinTest.label}
                                                    </div>
                                                </div>
                                            </div>
                                        </fieldset>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className='col d-flex'>
                        <div className={'card flex-grow-1 ' + styles.card__form}>
                            <div className={'card-header ' + styles.card_header__form}>Потребности пациента в энергии и белках</div>
                            <div className='card-body d-flex flex-column'>
                                <div className='row g-0 flex-grow-1'>
                                    <div className='col d-flex'>
                                        <div className={'card flex-grow-1 ' + styles.card__subform}>
                                            <div className='card-body'>
                                                <div className={'card-title ' + styles.card_title__division}>Активность</div>
                                                { generateMobilityFieldset() }
                                            </div>
                                        </div>
                                    </div>
                                    <div className='col d-flex'>
                                        <div className={'card flex-grow-1 ' + styles.card__subform}>
                                            <div className='card-body'>
                                                <div className={'card-title ' + styles.card_title__division}>Температура тела</div>
                                                {  generateTemperatureFieldset() }
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div className='row flex-grow-1'>
                                    <div className='col d-flex'>
                                        <div className={'card flex-grow-1 ' + styles.card__subform}>
                                            <div className='card-body'>
                                                <div className={'card-title ' + styles.card_title__division}>Фактор повреждения/увечья</div>
                                                { generateInjuryFieldset() }
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div className='row align-items-end'>
                    <div className='col'>
                        <label 
                            className={'form-label ' + styles.form_label__position} 
                            htmlFor='healthFacility'>Клиника, отделение:</label>
                        <input 
                            placeholder='9-я городская клиническая больница' 
                            id='healthFacility' 
                            className={'form-control ' + styles.input__form}
                            value={checkup.healthFacility} onChange={(event) =>  changeHealthFacilityHandler(event)} 
                            disabled={mode === 'display' || disabled} />
                    </div>
                    <div className='col'>
                        <label 
                            className={'form-label ' + styles.form_label__position} 
                            htmlFor='checkupDate'>Дата проведения:</label>
                        <div id='checkupDate' className={(validation.checkupDate.value ? ' is-invalid' : '')}>
                            <DateInput 
                                mode= 'checkupDate' 
                                disabled={mode === 'display' || disabled}
                                isValid={validation.checkupDate.value} 
                                selected={checkup.checkupDate} 
                                onChange={(event) => changeCheckupDateHandler(event)}
                                />
                        </div>
                        <div className='invalid-feedback'>
                            {validation.checkupDate.label}
                        </div>
                    </div>
                </div>
            </div>
            { 
                mode !== 'create' && 
                    <div className='card-footer'>
                        <div className='d-flex gap-3 mt-2 mb-2 flex-wrap'>
                            { generateButtons() }
                        </div>
                    </div>
            }
        </div>         
    );
});

export default CheckupForm;