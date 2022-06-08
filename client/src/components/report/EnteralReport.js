function EnteralReport(props) {
    const report = props.report;

    return (
        <>
            <h5 className='mb-3 text-center'>Рекомендации</h5>
            <table className='mb-3'>
                <tbody>
                    <tr>
                        <td className='fw-bold pe-2'>Рекомендовано применение препарата «Энтерал» (мл/сут):</td>
                        <td>{report.enteral}</td>
                    </tr>
                </tbody>
            </table>
            <div className='mb-3 fw-bold'>Дополнительно рекомендовано получать:</div>
            <table className='mb-3'>
                <tbody>
                    <tr>
                        <td className='fw-bold pe-2'>Белки (г/сут):</td>
                        <td>{report.nutrientNeeds.protein}</td>
                    </tr>
                    <tr>
                        <td className='fw-bold pe-2'>Количество небелковых калорий (ккал/сут):</td>
                        <td>                                                    
                            {
                                report.nutrientNeeds.nonprotein.number  + ' (из них углеводами ' +
                                report.nutrientNeeds.nonprotein.carbs + ' ккал/сут и жирами ' +
                                report.nutrientNeeds.nonprotein.fat + ' ккал/сут)'
                            }
                        </td>
                    </tr>
                </tbody>
            </table>
            <div className='fw-bold mb-3'>Получаемые нутриенты в составе препарата «Энтерал»:</div>
            <table className='mb-4 ms-2'>
                <tbody>
                    <tr>
                        <th className='fw-bold pe-2'>Название аминокислоты</th>
                        <th className='fw-bold pe-2'>Количество (мг)</th>
                        <th className='fw-bold'>% рекомендуемой суточной дозы</th>
                    </tr>
                    <tr>
                        <td className='fw-bold pe-2'>Глицин</td>
                        <td className='pe-2'>{report.nutrients.aminoAcids.glycine.amount}</td>
                        <td>{report.nutrients.aminoAcids.glycine.percentage}</td>
                    </tr>
                    <tr>
                        <td className='fw-bold pe-2'>Селен-метионин</td>
                        <td className='pe-2'>{report.nutrients.aminoAcids.selenomethionine.amount}</td>
                        <td>{report.nutrients.aminoAcids.selenomethionine.percentage}</td>
                    </tr>
                    <tr>
                        <td className='fw-bold pe-2'>L-орнитин-L-аспартат</td>
                        <td className='pe-2'>{report.nutrients.aminoAcids.LOrnithineLAspartate.amount}</td>
                        <td>{report.nutrients.aminoAcids.LOrnithineLAspartate.percentage}</td>
                    </tr>
                    <tr>
                        <td className='fw-bold pe-2'>L-пролин</td>
                        <td className='pe-2'>{report.nutrients.aminoAcids.LProline.amount}</td>
                        <td>{report.nutrients.aminoAcids.LProline.percentage}</td>
                    </tr>
                    <tr>
                        <td className='fw-bold pe-2'>L-серин</td>
                        <td className='pe-2'>{report.nutrients.aminoAcids.LSerine.amount}</td>
                        <td>{report.nutrients.aminoAcids.LSerine.percentage}</td>
                    </tr>
                    <tr>
                        <td className='fw-bold pe-2'>L-аспарагиновая кислота</td>
                        <td className='pe-2'>{report.nutrients.aminoAcids.LAsparticAcid.amount}</td>
                        <td>{report.nutrients.aminoAcids.LAsparticAcid.percentage}</td>
                    </tr>
                    <tr>
                        <td className='fw-bold pe-2'>L-глютаминовая кислота</td>
                        <td className='pe-2'>{report.nutrients.aminoAcids.LGlutamicAcid.amount}</td>
                        <td>{report.nutrients.aminoAcids.LGlutamicAcid.percentage}</td>
                    </tr>
                    <tr>
                        <td className='fw-bold pe-2'>L-фенилаланин</td>
                        <td className='pe-2'>{report.nutrients.aminoAcids.LPhenylalanine.amount}</td>
                        <td>{report.nutrients.aminoAcids.LPhenylalanine.percentage}</td>
                    </tr>
                    <tr>
                        <td className='fw-bold pe-2'>L-тирозин</td>
                        <td className='pe-2'>{report.nutrients.aminoAcids.LTyrosine.amount}</td>
                        <td>{report.nutrients.aminoAcids.LTyrosine.percentage}</td>
                    </tr>
                    <tr>
                        <td className='fw-bold pe-2'>L-лейцин</td>
                        <td className='pe-2'>{report.nutrients.aminoAcids.LLeucine.amount}</td>
                        <td>{report.nutrients.aminoAcids.LLeucine.percentage}</td>
                    </tr>
                    <tr>
                        <td className='fw-bold pe-2'>L-лизин-хлорид</td>
                        <td className='pe-2'>{report.nutrients.aminoAcids.LLysineHydrochloride.amount}</td>
                        <td>{report.nutrients.aminoAcids.LLysineHydrochloride.percentage}</td>
                    </tr>
                    <tr>
                        <td className='fw-bold pe-2'>L-изолейцин</td>
                        <td className='pe-2'>{report.nutrients.aminoAcids.LIsoleucine.amount}</td>
                        <td>{report.nutrients.aminoAcids.LIsoleucine.percentage}</td>
                    </tr>
                    <tr>
                        <td className='fw-bold pe-2'>L-метионин</td>
                        <td className='pe-2'>{report.nutrients.aminoAcids.LMethionine.amount}</td>
                        <td>{report.nutrients.aminoAcids.LMethionine.percentage}</td>
                    </tr>
                    <tr>
                        <td className='fw-bold pe-2'>L-валин</td>
                        <td className='pe-2'>{report.nutrients.aminoAcids.LValine.amount}</td>
                        <td>{report.nutrients.aminoAcids.LValine.percentage}</td>
                    </tr>
                    <tr>
                        <td className='fw-bold pe-2'>L-триптофан</td>
                        <td className='pe-2'>{report.nutrients.aminoAcids.LTryptophan.amount}</td>
                        <td>{report.nutrients.aminoAcids.LTryptophan.percentage}</td>
                    </tr>
                    <tr>
                        <td className='fw-bold pe-2'>L-треонин</td>
                        <td className='pe-2'>{report.nutrients.aminoAcids.LThreonine.amount}</td>
                        <td>{report.nutrients.aminoAcids.LThreonine.percentage}</td>
                    </tr>
                    <tr>
                        <td className='fw-bold pe-2'>L-аргинин</td>
                        <td className='pe-2'>{report.nutrients.aminoAcids.LArginine.amount}</td>
                        <td>{report.nutrients.aminoAcids.LArginine.percentage}</td>
                    </tr>
                    <tr>
                        <td className='fw-bold pe-2'>L-гистидин</td>
                        <td className='pe-2'>{report.nutrients.aminoAcids.LHistidine.amount}</td>
                        <td>{report.nutrients.aminoAcids.LHistidine.percentage}</td>
                    </tr>
                    <tr>
                        <td className='fw-bold pe-2'>L-аланин</td>
                        <td className='pe-2'>{report.nutrients.aminoAcids.LAlanine.amount}</td>
                        <td>{report.nutrients.aminoAcids.LAlanine.percentage}</td>
                    </tr>
                    <tr>
                        <th className='fw-bold pe-2 pt-3'>Витамины</th>
                        <th className='fw-bold pe-2 pt-3'>Количество (мг)</th>
                        <th className='fw-bold pt-3'>% рекомендуемой суточной дозы</th>
                    </tr>
                    <tr>
                        <td className='fw-bold pe-2'>Рибофлавин</td>
                        <td className='pe-2'>{report.nutrients.vitamins.riboflavin.amount}</td>
                        <td>{report.nutrients.vitamins.riboflavin.percentage}</td>
                    </tr>
                    <tr>
                        <td className='fw-bold pe-2'>Пиродоксина гидрохлорид</td>
                        <td className='pe-2'>{report.nutrients.vitamins.pyridoxineHydrochloride.amount}</td>
                        <td>{report.nutrients.vitamins.pyridoxineHydrochloride.percentage}</td>
                    </tr>
                    <tr>
                        <td className='fw-bold pe-2'>Никотинамид</td>
                        <td className='pe-2'>{report.nutrients.vitamins.nicotinamide.amount}</td>
                        <td>{report.nutrients.vitamins.nicotinamide.percentage}</td>
                    </tr>
                </tbody>
            </table>
        </>
    );
}

export default EnteralReport;