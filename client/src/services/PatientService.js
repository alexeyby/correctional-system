import axios from 'axios';

const PATIENT_API_BASE_URL = `${window.location.protocol}//${window.location.hostname}:8080/api/v1/patients`;

class PatientService {
    static getPatients() {
        return axios.get(PATIENT_API_BASE_URL);
    }

    static getPatient(id) {
        return axios.get(`${PATIENT_API_BASE_URL}/${id}`);
    }

    static postPatient(patient) {
        return axios.post(PATIENT_API_BASE_URL, patient);
    }

    static putPatient(patient) {
        return axios.put(PATIENT_API_BASE_URL, patient);
    }

    static deletePatient(id) {
        return axios.delete(`${PATIENT_API_BASE_URL}/${id}`);
    }
}

export default PatientService;