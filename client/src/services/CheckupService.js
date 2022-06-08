import axios from 'axios';

const CHECKUP_API_BASE_URL = `${window.location.protocol}//${window.location.hostname}:8080/api/v1/checkups`;

class CheckupService {
    static getCheckupByPatientId(patientId) {
        return axios.get(`${CHECKUP_API_BASE_URL}/by-patient-id/${patientId}`);
    }

    static postCheckup(checkup) {
        return axios.post(CHECKUP_API_BASE_URL, checkup);
    }

    static putCheckup(checkup) {
        return axios.put(CHECKUP_API_BASE_URL, checkup);
    }

    static deleteCheckup(id) {
        return axios.delete(`${CHECKUP_API_BASE_URL}/${id}`);
    }
}

export default CheckupService;