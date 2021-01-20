import axios from 'axios'

const AUCTIONS_API = 'http://localhost:8080/api'
const GET_AUCTIONS_API_URL = `${AUCTIONS_API}/auctions`

class AuctionsDataService {

    retrieveAllAuctions() {
        return axios.get(`${GET_AUCTIONS_API_URL}`);
    }
}

export default new AuctionsDataService()

