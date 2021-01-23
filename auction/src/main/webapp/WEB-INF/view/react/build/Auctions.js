const { Component } = React;
const { render } = ReactDOM;
const axios = axios;

const AUCTIONS_API = 'http://localhost:8080/api'
const GET_AUCTIONS_API_URL = `${AUCTIONS_API}/auctions`

class ListAuctionsComponent extends Component {
	
    constructor(props) {
        super(props)
        this.state = {
            allAuctions: [],
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
                    this.setState({ allAuctions: response.data });         
				}
            )
    }

	convertTimestampToDatetime(timestamp) {
		var date = new Date(timestamp * 1000);
		const enUKFormatter = new Intl.DateTimeFormat('en-UK');
		return enUKFormatter.format(date);
	}
	
	getStatusFromActive(active) {
		if (active == true)
			return "Active";
		else {
			return "Closed";
		}
	}
	
	checkTopBid(bid) {
		if (bid == -1)
			return "no bids yet";
		else 
			return bid;
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
								<th>Time Limit</th>
								<th>Top Bid</th>
								<th>Status</th>
								<th>Make Bid</th>
                            </tr>
                        </thead>
                        <tbody>
							{
								this.state.allAuctions.map(
									auction =>
									<tr key={auction.id}>
		                                <td>{auction.id}</td>
										<td>{auction.name}</td>
										<td>{auction.description}</td>
										<td>{"" + auction.timeLimit}</td>
										<td style={{ textAlign: 'center', verticalAlign: 'middle'}}>{this.checkTopBid(auction.topBidValue)}</td>
										<td style={{ textAlign: 'center', verticalAlign: 'middle'}}>{this.getStatusFromActive(auction.active)}</td>
										<td style={{ textAlign: 'center', verticalAlign: 'middle'}}>nothing here yet</td>	                                
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