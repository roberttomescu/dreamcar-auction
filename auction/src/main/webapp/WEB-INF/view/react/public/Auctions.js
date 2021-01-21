const { Component } = React;
const { render } = ReactDOM;
const axios = axios;

const AUCTIONS_API = 'http://localhost:8080/api'
const GET_AUCTIONS_API_URL = `${AUCTIONS_API}/auctions`

class ListAuctionsComponent extends Component {
	
    constructor(props) {
        super(props)
        this.state = {
            auctions: [],
            message: null
        }
        this.refreshAuctions = this.refreshAuctions.bind(this)
    }

    componentDidMount() {
        this.refreshAuctions();
    }

    retrieveAllAuctions() {
        return axios.get(`${GET_AUCTIONS_API_URL}`);
    }

    refreshAuctions() {
        this.retrieveAllAuctions()
            .then(
                response => {
                    this.setState({ auctions: response.data });         
				}
            )
    }

    render() {
        return (
            <div className="container">
                <h3>All Auctions</h3>
                <div className="container">
                    <table className="table">
                        <thead>
                            <tr>
                                <th>Id</th>
                                <th>Name</th>
								<th>Description</th>
								<th>Time_Limit</th>
                            </tr>
                        </thead>
                        <tbody>
							{
								this.state.auctions.map(
									auction =>
									<tr key={auction.id}>
		                                <td>{auction.id}</td>
										<td>{auction.name}</td>
										<td>{auction.description}</td>
										<td>{auction.timeLimit}</td>		                                
		                            </tr> 
								)
							}
                        </tbody>
                    </table>
                </div>
            </div>
        )
    }
}

ReactDOM.render(<ListAuctionsComponent />, document.getElementById('all-auctions'));