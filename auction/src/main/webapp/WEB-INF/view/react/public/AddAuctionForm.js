const { Component } = React;
const { render } = ReactDOM;
const axios = axios;

const ADD_AUCTION_API = 'http://localhost:8080/api/auction'

class AddAuctionForm extends Component{
	constructor(props) {
    super(props);
    this.state = {name: '',
				description: '',
				timeout_date: '',
				timeout_hour: '',
				pricelimit: ''};

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleChange(event) {
  	const value = event.target.value;
	this.setState({
		...this.state, [event.target.name]: value});
  }

  handleSubmit(event) {
    event.preventDefault();
	var time = this.calculateTime(this.state.timeout_hour);
    const body = { name: this.state.name,
					description: this.state.description,
					timeLimit: this.state.timeout_date + " " + this.state.timeout_hour,
					priceLimit: this.state.pricelimit
					 };
    axios.post(ADD_AUCTION_API, body)
        .then(response => {
		  console.log('Success', response);
		  window.location.reload();	
		}).catch(error => {
		  console.log('Error', error);
		});
  }

   calculateTime(time) {
	  var [h,m] = time.split(":");
	  var hours = h%12 + 12*(h%12 == 0);
	  var minutes = m;
	  var ampm = "AM";
	  if (h >= 12)
		var ampm = "PM"
	  return "" + hours + ":" + minutes + " " + ampm;
}
	
    render() {
		return (	
		<div>		
			<div>
	            <h1>Section for adding a new auction</h1>
	        </div>
			<form onSubmit={this.handleSubmit}>
			<div>
				<label>
					Name 
				</label>
				<input type="text" name="name" value={this.state.name} onChange={this.handleChange} />
			</div>
			<div>
				<label>
					Description 
				</label>
				<textarea type="text" name="description" value={this.state.description} onChange={this.handleChange} />
			</div>
			<div>
				<label>
					Timeout
				</label>
				<input type="date" name="timeout_date" value={this.state.timeout_date} onChange={this.handleChange} />
				<input type="time" name="timeout_hour" value={this.state.timeout_hour} onChange={this.handleChange} />
			</div>
			<div>
				<label>
					Price Limit
				</label>
				<input type="number" name="pricelimit" value={this.state.pricelimit} onChange={this.handleChange} min="0"/>
			</div>
			<div>
				<input type="submit" value="Add Auction" />
			</div>
			 </form>
		</div>
		);
	}
}

ReactDOM.render(<AddAuctionForm />, document.getElementById('add-auction-form'));