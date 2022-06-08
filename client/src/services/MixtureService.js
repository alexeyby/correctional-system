import axios from 'axios';

const MIXTURE_API_BASE_URL = `${window.location.protocol}//${window.location.hostname}:8080/api/v1/mixtures`;

class MixtureService {
    static generateReport(mixture, patientId) {
        return axios.get(`${MIXTURE_API_BASE_URL}/generate-report/${mixture}/${patientId}`);
    }

    static generateReportDocx(mixture, patientId) {
        return axios.get(`${MIXTURE_API_BASE_URL}/generate-report/docx/${mixture}/${patientId}`, { responseType: 'blob' });
    }
}

export default MixtureService;