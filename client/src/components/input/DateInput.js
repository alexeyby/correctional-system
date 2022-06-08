import { useState, useMemo } from 'react';
import DatePicker, { CalendarContainer, registerLocale } from 'react-datepicker';
import Select from 'react-select';
import 'react-datepicker/dist/react-datepicker.css';
import ru from 'date-fns/locale/ru';
import styles from './DateInput.module.css';

registerLocale('ru', ru);

const months = [
    { value: 0, label: 'Январь' },
    { value: 1, label: 'Февраль' },
    { value: 2, label: 'Март' },
    { value: 3, label: 'Апрель' },
    { value: 4, label: 'Май' },
    { value: 5, label: 'Июнь' },
    { value: 6, label: 'Июль' },
    { value: 7, label: 'Август' },
    { value: 8, label: 'Сентябрь' },
    { value: 9, label: 'Октбярь' },
    { value: 10, label: 'Ноябрь' },
    { value: 11, label: 'Декабрь' }
];

const getDateSelectStyles = (width) => {
    return {     
        container: (base) => ({
            ...base,
            width: width
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
    }
};

function DateInput(props) {
    const lowerDate = useMemo(() => {
        let date;
        if (props.mode === 'checkupDate') {
            date = new Date();
            date.setFullYear(date.getFullYear() - 1);
        } else {
            date = new Date('1900-01-01');
        }
        return date;
    }, [props.mode]);

    const upperDate = useMemo(() => {
        let date = new Date();
        if (props.mode === 'birthdate') date.setFullYear(date.getFullYear() - 18);
        return date;
    }, [props.mode]);

    const [years, lowerYear, upperYear, upperMonth] = useMemo(() => {
        let lowerDateYear = lowerDate.getFullYear(),
        upperDateYear = upperDate.getFullYear(),
        dateYears = [];
        for (let year = lowerDateYear; year <= upperDateYear; year++) dateYears.push({ value: year, label: year });
        return [dateYears, lowerDateYear, upperDateYear, upperDate.getMonth()];
    }, [lowerDate, upperDate]);

    const [{ selectedMonth, selectedYear, prevMonth, nextMonth }, setState] = useState(
        { 
            selectedMonth: '', 
            selectedYear: '',
            prevMonth: '',
            nextMonth: ''
        } 
    );

    function selectedMonthHandler(event) {
        setState((state) => {
            return { ...state, selectedMonth: event, prevMonth: lowerYear !== selectedYear.value || event.value !== 0, 
                     nextMonth: upperYear !== selectedYear.value || event.value !== 11 };
        });
    };

    function selectedYearHandler(event) {
        setState((state) => {
            return { ...state, selectedYear: event, prevMonth: lowerYear !== event.value || selectedMonth.value !== 0, 
                     nextMonth: upperYear !== event.value || selectedMonth.value !== 11 };
        });
    };

    function calendarOpenHandler() {
        let currentSelectedMonth = !!props.selected ? months[props.selected.getMonth()] : months[upperMonth],
        currentSelectedYear = !!props.selected ? years[props.selected.getFullYear() - lowerYear] : years[upperYear - lowerYear],
        currentPrevMonth = currentSelectedMonth.value !== 0 ? true : lowerYear <= currentSelectedYear.value - 1,
        currentNextMonth = currentSelectedMonth.value !== 11 ? true : currentSelectedYear.value + 1 <= upperYear;
        setState((state) => {
            return {
                ...state,
                selectedMonth: currentSelectedMonth,
                selectedYear: currentSelectedYear,
                prevMonth: currentPrevMonth,
                nextMonth: currentNextMonth
            }
        });
    };

    function decreaseSelectedMonth() {
        let currentSelectedYear;
        switch (selectedMonth.value) {
            case 0:
                currentSelectedYear = years[selectedYear.value - lowerYear - 1];
                setState((state) => {
                    return { ...state, selectedMonth: months[11], selectedYear: currentSelectedYear };
                });
                break;
            case 1:
                currentSelectedYear = years[selectedYear.value - lowerYear - 1];
                setState((state) => {
                    return { ...state, selectedMonth: months[selectedMonth.value - 1], prevMonth: currentSelectedYear !== undefined };
                });
                break;
            case 11:
                setState((state) => {
                    return { ...state, selectedMonth: months[selectedMonth.value - 1], nextMonth: true };
                });
                break;
            default:
                setState((state) => {
                    return { ...state, selectedMonth: months[selectedMonth.value - 1] };
                });
                break;
        }
    };

    function increaseSelectedMonth() {
        let currentSelectedYear;
        switch (selectedMonth.value) {
            case 0:
                setState((state) => {
                    return { ...state, selectedMonth: months[selectedMonth.value + 1], prevMonth: true };
                });
                break;
            case 10:
                currentSelectedYear = years[selectedYear.value - lowerYear + 1];
                setState((state) => {
                    return { ...state, selectedMonth: months[selectedMonth.value + 1], nextMonth: currentSelectedYear !== undefined };
                });
                break;
            case 11:
                currentSelectedYear = years[selectedYear.value - lowerYear + 1];
                setState((state) => {
                    return { ...state, selectedMonth: months[0], selectedYear: currentSelectedYear };
                });
                break;
            default:
                setState((state) => {
                    return { ...state, selectedMonth: months[selectedMonth.value + 1] };
                });
                break;
        }
    };

    return (
        <DatePicker
            className={'form-control ' + styles.input__form + (props.isValid ? ' is-invalid' : '')}
            selected={props.selected}
            onChange={(event) => props.onChange(event)}
            dateFormat='dd.MM.yyyy' 
            locale='ru' 
            placeholderText='дд.мм.гггг'
            disabled={props.disabled}
            startDate={props.selected}
            minDate={lowerDate}
            maxDate={upperDate}
            onCalendarOpen={calendarOpenHandler}
            highlightDates={
                [
                    {
                        'react-datepicker__day--highlighted-custom-1': !!props.selected ? [ props.selected ] : []
                    }
                ]
            }
            withPortal
            calendarContainer={(
                    {
                        className, 
                        children
                    }
                ) => {
                    return (
                        <div className={styles.calendar_container}>
                            <CalendarContainer className={className}>
                                <div className='position-relative'>{children}</div>
                            </CalendarContainer>
                        </div>
                    );
                }
            }
            renderCustomHeader={(
                {
                    decreaseMonth,
                    increaseMonth,
                    changeMonth,
                    changeYear
                }
                ) => {       
                    return (
                        <div className='d-flex justify-content-center gap-2 mx-3 mt-3 mb-2'>
                            <button 
                                className={'btn me-2 ' + styles.btn__navigation}
                                disabled={!prevMonth}
                                onClick={() => { 
                                        decreaseMonth(); 
                                        decreaseSelectedMonth(); 
                                    }
                                }>
                                &lt;
                            </button>
                            <div className='flex-grow-1 d-flex justify-content-center gap-1'>
                                <Select styles={getDateSelectStyles('7.5rem')}
                                    options={months}
                                    defaultValue={selectedMonth}
                                    onChange={(event) => { 
                                            changeMonth(event.value); 
                                            selectedMonthHandler(event); 
                                        }
                                    }
                                />
                                <Select styles={getDateSelectStyles('5.5rem')}
                                    options={years}
                                    defaultValue={selectedYear}
                                    onChange={(event) => { 
                                            changeYear(event.value); 
                                            selectedYearHandler(event); 
                                        }
                                    }
                                />
                            </div>
                            <button 
                                className={'btn ms-2 ' + styles.btn__navigation}
                                disabled={!nextMonth}
                                onClick={() => { 
                                        increaseMonth(); 
                                        increaseSelectedMonth();
                                    }
                                }>
                                &gt;
                            </button>
                        </div>
                    );
                }
            }
            />
    );
}

export default DateInput;