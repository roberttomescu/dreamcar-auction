const { Component } = React;
const { render } = ReactDOM;
const axios = axios;

const AUCTIONS_API = 'http://localhost:8080/api'
const BIDS_API = 'http://localhost:8080/api-bid'
const GET_AUCTIONS_API_URL = `${AUCTIONS_API}/auctions`
const GET_YOUR_BIDS_API_URL = `${BIDS_API}/yourBids`
const ADD_BID_API_URL =  `${BIDS_API}/bid`

class ListAuctionsComponent extends Component {
	
    constructor(props) {
        super(props)
        this.state = {
            allAuctions: [],
            yourBids: [],
			yourAuctions: [],
			auctionsLoaded: 0,
			bidsLoaded: 0,
			yourAuctionsLoaded: 0,
			prices: {}
        }
        this.refreshAuctions = this.refreshAuctions.bind(this)
		this.refreshBids = this.refreshBids.bind(this)
		this.refreshYourAuctions = this.refreshYourAuctions.bind(this)
    }

    componentDidMount() {
        this.refreshAuctions();
		this.refreshBids();
    }

    retrieveAllAuctions() {
        return axios.get(`${GET_AUCTIONS_API_URL}`);
    }

	retrieveYourBids() {
		return axios.get(`${GET_YOUR_BIDS_API_URL}`);
	}

    refreshAuctions() {
        this.retrieveAllAuctions()
            .then(
                response => {
                    this.setState({ ...this.state, allAuctions: response.data, auctionsLoaded: 1 });         
				}
            )
    }

    refreshBids() {
        this.retrieveYourBids()
            .then(
                response => {
                    this.setState({ ...this.state, yourBids: response.data, bidsLoaded: 1 });         
				}
            )
    }

	refreshYourAuctions() {
		var auctions = this.state.allAuctions
		var yourAuctions = []
		var uniqueAuctionIds = []
		
		// get auction ids for auctions you participate in 
		this.state.yourBids.forEach(bid => {
			if (uniqueAuctionIds.indexOf(bid.auctionId) === -1) {
				uniqueAuctionIds.push(bid.auctionId)
			}
		})
		
		// for each auction you participate in, get the top bid from your bids and add to auction object as yourBid
		for (var i = 0;i < uniqueAuctionIds.length; i++) {
			
			//find your top bid for each unique auction id
			var topBid = null 
			for (var j = 0; j < this.state.yourBids.length; j++) {
				var bid = this.state.yourBids[j]
				if (bid.auctionId === uniqueAuctionIds[i]) {
					if (topBid == null) { 
						topBid = bid
					}
					else {
						if (topBid.price > bid.price) {
							topBid = bid		
						}
					}
				}
			}
			// add your top bid to the auction object 
			auctions.forEach(auction => {
				if (auction.id === uniqueAuctionIds[i]) {
					Object.assign(auction, {yourBidValue: topBid.price})
					yourAuctions.push(auction)
				}
			})
		}
		
		this.setState({ ...this.state, yourAuctions: yourAuctions, yourAuctionsLoaded: 1})
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
			return "No bids yet";
		else 
			return bid;
	}
	
	getYourBidStatusFromAuction(auction) {
		if (auction.active == true) {
			if (auction.topBidValue == auction.yourBidValue)
				return "Winning"
			else
				return "Losing"	
		}
		else {
			if (auction.topBidValue == auction.yourBidValue)
				return "WON"
			else
				return "Lost"
		}
	}
	
	checkIfTopBidderByAuction(auction) {
		if (auction.active == false)
			return "Auction closed"
		for (var i = 0; i < this.state.yourAuctions.length; i++) {
			var yourAuction = this.state.yourAuctions[i]
			if (auction.id == yourAuction.id) {
				if (yourAuction.yourBidValue == yourAuction.topBidValue) {
					return "Already top bidder"
				}
				else {
					return 	this.makeBidSubmit(auction)
				}
			}
		}
		return 	this.makeBidSubmit(auction)
	}
	
	makeBidSubmit(auction) {
		return (
			<form onSubmit={this.handleSubmit.bind(this, auction)}>
			<input type="number" name={"price-" + auction.id} onChange={this.handleChange.bind(this, auction)} min="0" max={auction.topBidValue == -1 ? "" : auction.topBidValue} step="0.01" />
			<input type="submit" value="Bid" />
			</form>
		)
	}

  handleSubmit(auction, event) {
    event.preventDefault();
	var auctionId = auction.id
	var price = this.state["price-" + auction.id]
    const body = { auctionId: auctionId,
					price: price
				 }
    axios.post(ADD_BID_API_URL, body)
        .then(response => {
		  console.log('Success', response);
		  window.location.reload();	
		}).catch(error => {
		  console.log('Error', error);
		});
  }

  handleChange(auction, event) {
  	const value = event.target.value;
	this.setState({
		...this.state, [event.target.name]: value } );
  }

    render() {
		const rootStyle1 = {
			  right: "0",
			  left: "0",
			  marginRight: "auto",
			  marginLeft: "auto",
			  overflow: "hidden",
			  display: "flex",
			  alignItems: "center",
			  justifyContent: "center",
			  width: "100%",
			  borderRadius: ".19rem",
			  boxShadow: "0px .19rem .41rem rgba(0,0,0,.25)"
		}
		const rootStyle2 = {
			  right: "0",
			  left: "0",
			  marginRight: "auto",
			  marginLeft: "auto",
			  overflow: "hidden",
			  display: "flex",
			  alignItems: "center",
			  justifyContent: "center",
			  width: "100%",
			  borderRadius: ".19rem",
			  boxShadow: "0px .19rem .41rem rgba(0,0,0,.25)"
		}
		const tableStyle = {
		    borderCollapse: "separate",
			borderSpacing: "50px 10px"		
		}
		if (this.state.auctionsLoaded === 1 && this.state.bidsLoaded === 1 && this.state.yourAuctionsLoaded === 0)
			this.refreshYourAuctions();
        return (
			<div className="container">
				<div className="container" style={rootStyle1}>
					<div className="container">
						<h2>Your Bids</h2>
	                    <table className="table" style={tableStyle}>
	                        <thead>
	                            <tr>
	                                <th>Id</th>
	                                <th>Name</th>
									<th>Time Limit</th>
									<th>Top Bid</th>
									<th>Your Bid</th>
									<th>Status</th>
									<th>Renew Bid</th>
	                            </tr>
	                        </thead>
	                        <tbody>
								{
									this.state.yourAuctions.map(
										auction =>
										<tr key={auction.id}>
			                                <td>{auction.id}</td>
											<td>{auction.name}</td>
											<td>{"" + auction.timeLimit}</td>
											<td style={{ textAlign: 'center', verticalAlign: 'middle'}}>{this.checkTopBid(auction.topBidValue)}</td>
											<td style={{ textAlign: 'center', verticalAlign: 'middle'}}>{auction.yourBidValue}</td>
											<td style={{ textAlign: 'center', verticalAlign: 'middle'}}>{this.getYourBidStatusFromAuction(auction)}</td>
											<td style={{ textAlign: 'center', verticalAlign: 'middle'}}>{this.checkIfTopBidderByAuction(auction)}</td>	                                
			                            </tr> 
									)
								}
	                        </tbody>
	                    </table>
	                </div>

				</div>
				<br/>
	            <div className="container" style={rootStyle2}>
	                <div className="container">
		                <h2>All Auctions</h2>
	                    <table className="table" style={tableStyle}>
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
											<td style={{ textAlign: 'center', verticalAlign: 'middle'}}>{this.checkIfTopBidderByAuction(auction)}</td>	                                
			                            </tr> 
									)
								}
	                        </tbody>
	                    </table>
	                </div>
	            </div>
			</div>
        )
    }
}

ReactDOM.render(<ListAuctionsComponent />, document.getElementById('all-auctions'));