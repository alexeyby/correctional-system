import { useState, useEffect, useRef } from 'react';
import { useParams } from 'react-router-dom';
import FileSaver from 'file-saver';
import { useReactToPrint } from 'react-to-print';
import Loading from '../loading/Loading';
import EnteralReport from './EnteralReport';
import MixtureService from '../../services/MixtureService';
import styles from './Report.module.css';
import download from '../../assets/download.svg';
import print from '../../assets/print.svg';

const mixtures = {
    enteral: 'ENTERAL',
    peptomen: 'PEPTOMEN'
};

const pageStyle = `
  @page {
    margin: 20mm;
  }
`;

function Report() {
    const params = useParams();

    const mixture = mixtures[params.mixture];

    const reportRef = useRef();

    const [{ isLoading, showLoading, report }, setState] = useState(
        {
            isLoading: true,
            showLoading: false,
            report: ''
        }
    )

    useEffect(() => {
        MixtureService.generateReport(mixture, params.id).then((res) => {
            let newReport = res.data;

            setState((state) => {
                return { ...state, isLoading: false, report: newReport };
            });
        });

        setTimeout(() => {
            setState((state) => {
              return { ...state, showLoading: true };
            });
        }, 100);
    }, [mixture, params.id]);

    const printReport = useReactToPrint({
        content: () => reportRef.current,
        pageStyle: () => pageStyle
    });

    function downloadDocx() {
        MixtureService.generateReportDocx(mixture, params.id).then((res) => {
            let fileName = `report_${params.mixture}_${report.historyNumber}.docx`;
            FileSaver.saveAs(res.data, fileName);
        });
    };

    function generateReportMixture() {
        switch (mixture) {
            case 'ENTERAL':
                return <EnteralReport report={report.recommendations} />
            case 'PEPTOMEN':
                return <></>
            default:
        }
    };

    return (
        <>
            { 
                !isLoading &&              
                <div className='container-fluid'>
                    <div className='row mt-5 mb-4'>
                        <div className='col d-flex justify-content-center'>
                            <div className={styles.report_box}>
                                <div ref={reportRef} className={styles.report}>
                                    <table className='mb-4 w-100'>
                                        <tbody>
                                            <tr>
                                                <td className='fw-bold'>Клиника, отделение:</td>
                                                <td className='fw-bold' align='right'>№ истории болезни:</td>
                                            </tr>
                                            <tr>
                                                <td>{report.healthFacility}</td>
                                                <td align='right'>{report.historyNumber}</td>
                                            </tr>
                                        </tbody>
                                    </table> 
                                    <h5 className='mb-3 text-center'>Персональные данные</h5>
                                    <table className='mb-4'>
                                        <tbody>
                                            <tr>
                                                <td className='fw-bold pe-2'>Фамилия:</td>
                                                <td>{report.personalData.lastName}</td>
                                            </tr>
                                            <tr>
                                                <td className='fw-bold pe-2'>Имя:</td>
                                                <td>{report.personalData.firstName}</td>
                                            </tr>
                                            <tr>
                                                <td className='fw-bold pe-2'>Отчество:</td>
                                                <td>{report.personalData.surname}</td>
                                            </tr>
                                            <tr>
                                                <td className='fw-bold pe-2'>Пол:</td>
                                                <td>{report.personalData.sex}</td>
                                            </tr>
                                            <tr>
                                                <td className='fw-bold pe-2'>Возраст (полных лет):</td>
                                                <td>{report.personalData.age}</td>
                                            </tr>
                                            <tr>
                                                <td className='fw-bold pe-2'>Дата обследования:</td>
                                                <td>{report.personalData.checkupDate}</td>
                                            </tr>
                                            <tr>
                                                <td className='fw-bold pe-2'>Печеночная и (или) почечная недостаточность:</td>
                                                <td>{report.personalData.failure}</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    <h5 className='mb-3 text-center'>Антропометрические данные</h5>
                                    <table className='mb-4'>
                                        <tbody>
                                            <tr>
                                                <td className='fw-bold pe-2'>Вес (кг):</td>
                                                <td>{report.anthroData.weight}</td>
                                            </tr>
                                            <tr>
                                                <td className='fw-bold pe-2'>Рост (см):</td>
                                                <td>{report.anthroData.height}</td>
                                            </tr>
                                            <tr>
                                                <td className='fw-bold pe-2'>Толщина КЖСТ (мм):</td>
                                                <td>{report.anthroData.skinfoldThickness}</td>
                                            </tr>
                                            <tr>
                                                <td className='fw-bold pe-2'>Окружность плеча (см):</td>
                                                <td>{report.anthroData.armCircumference}</td>
                                            </tr>
                                        </tbody>
                                    </table> 
                                    <h5 className='mb-3 text-center'>Лабораторные данные</h5>
                                    <table className='mb-4'>
                                        <tbody>
                                            <tr>
                                                <td className='fw-bold pe-2'>Альбумин (г/л):</td>
                                                <td>{report.labData.albumin}</td>
                                            </tr>
                                            <tr>
                                                <td className='fw-bold pe-2'>Трансферрин (г/л):</td>
                                                <td>{report.labData.transferrin}</td>
                                            </tr>
                                            <tr>
                                                <td className='fw-bold pe-2'>Лимфоциты (10^9/л):</td>
                                                <td>{report.labData.lymphocyte}</td>
                                            </tr>
                                            <tr>
                                                <td className='fw-bold pe-2'>Кожная реакция (мм):</td>
                                                <td>{report.labData.skinTest}</td>
                                            </tr>
                                        </tbody>
                                    </table>                                      
                                    <h5 className='mb-3 text-center'>Фактические потребности пациента</h5>
                                    <table className='mb-4'>
                                        <tbody>
                                            <tr>
                                                <td className='fw-bold pe-2'>Активность:</td>
                                                <td>{report.patientActualNeeds.mobility}</td>
                                            </tr>
                                            <tr>
                                                <td className='fw-bold pe-2'>Температура тела:</td>
                                                <td>{report.patientActualNeeds.temperature}</td>
                                            </tr>
                                            <tr>
                                                <td className='fw-bold pe-2'>Фактор повреждения/увечья:</td>
                                                <td>{report.patientActualNeeds.injury}</td>
                                            </tr>
                                        </tbody>
                                    </table> 
                                    <h5 className='mb-3 text-center'>Вычисления</h5>
                                    <table className='mb-3'>
                                        <tbody>
                                            <tr>
                                                <td className='fw-bold pe-2'>Питательный статус по ИМТ:</td>
                                                <td>{report.calculations.BMINutritionalStatus}</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    <div className='mb-3 fw-bold'>Степень недостаточности питания:</div>
                                    <table className='mb-3 ms-2'>
                                        <tbody>
                                            <tr>
                                                <td className='fw-bold pe-2'>по ИМТ:</td>
                                                <td>{report.calculations.degreeMalnutrition.BMI}</td>
                                            </tr>
                                            <tr>
                                                <td className='fw-bold pe-2'>по ОМП:</td>
                                                <td>{report.calculations.degreeMalnutrition.SMV}</td>
                                            </tr>
                                            <tr>
                                                <td className='fw-bold pe-2'>по КЖСТ:</td>
                                                <td>{report.calculations.degreeMalnutrition.SFT}</td>
                                            </tr>
                                            <tr>
                                                <td className='fw-bold pe-2'>Альбумин:</td>
                                                <td>{report.calculations.degreeMalnutrition.albumin}</td>
                                            </tr>
                                            <tr>
                                                <td className='fw-bold pe-2'>Трансферрин:</td>
                                                <td>{report.calculations.degreeMalnutrition.transferrin}</td>
                                            </tr>
                                            <tr>
                                                <td className='fw-bold pe-2'>Лимфоциты:</td>
                                                <td>{report.calculations.degreeMalnutrition.lymphocyte}</td>
                                            </tr>
                                            <tr>
                                                <td className='fw-bold pe-2'>Кожная реакция:</td>
                                                <td>{report.calculations.degreeMalnutrition.skinTest}</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    <div className='mb-3 fw-bold'>Суммарная оценка:</div>
                                    <table className='mb-3 ms-2'>
                                        <tbody>
                                            <tr>
                                                <td className='fw-bold pe-2'>Тип белковой недостаточности:</td>
                                                <td>{report.calculations.totalValue.proteinEnergyMalnutrition}</td>
                                            </tr>
                                            <tr>
                                                <td className='fw-bold pe-2'>Степень недостаточности питания:</td>
                                                <td>{report.calculations.totalValue.totalDegreeMalnutrition}</td>
                                            </tr> 
                                        </tbody>
                                    </table>
                                    <div className='mb-3 fw-bold'>Потребности в основных нутриентах:</div>
                                    <table className='mb-4 ms-2'>
                                        <tbody>
                                            <tr>
                                                <td className='fw-bold pe-2'>Белки (г/сут):</td>
                                                <td>{report.calculations.nutrientNeeds.protein}</td>
                                            </tr>
                                            <tr>
                                                <td className='fw-bold pe-2'>Количество небелковых калорий (ккал/сут):</td>
                                                <td>
                                                    {
                                                        report.calculations.nutrientNeeds.nonprotein.number  + ' (из них углеводами ' +
                                                        report.calculations.nutrientNeeds.nonprotein.carbs + ' ккал/сут и жирами ' +
                                                        report.calculations.nutrientNeeds.nonprotein.fat + ' ккал/сут)'
                                                    }   
                                                </td>
                                            </tr> 
                                        </tbody>
                                    </table>
                                    { generateReportMixture() }
                                    <table className='mt-4'>
                                        <tbody>
                                            <tr>
                                                <td className='fw-bold pe-2'>Врач, проводивший исследование:</td>
                                                <td className='pe-2 align-bottom'>______________________</td>
                                                <td className='align-bottom'>______________________</td>
                                            </tr> 
                                            <tr>
                                                <td className='pe-2' />
                                                <td className='pe-2' align='center'>(подпись)</td>
                                                <td align='center'>(фамилия и.о.)</td>
                                            </tr> 
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className='row'>
                        <div className='col d-flex justify-content-center gap-5'>
                            <button className={'btn d-flex align-items-center ' + styles.btn__action} onClick={() => downloadDocx()}>
                                <img className='me-2' src={download} width='15' height='23' alt='download' />
                                <font size='+2'>Скачать</font>
                            </button>
                            <button className={'btn d-flex align-items-center ' + styles.btn__action} onClick={() => printReport()}>
                                <img className='me-2' src={print} width='25' height='25' alt='print' />
                                <font size='+2'>Печать</font>
                            </button>
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

export default Report;