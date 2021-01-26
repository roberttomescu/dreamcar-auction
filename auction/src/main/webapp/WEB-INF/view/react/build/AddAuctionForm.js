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
		const rootStyle = {
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
			  boxShadow: "0px .19rem .41rem rgba(0,0,0,.25)",
		}
		const formStyle = {
			fontFamily:"'Montserrat',sans-serif",
			position:"relative",
			paddingBlock: "50px"
		}
		const labelStyle = {
			marginBottom: "10px",
			display: "block"
		}
		return (	
		<div style={rootStyle}>		
			<form onSubmit={this.handleSubmit} style={formStyle}>
            <h1>Add a new auction</h1>
			<div style={labelStyle}>
				<label>
					Name 
				</label>
				<input type="text" name="name" value={this.state.name} onChange={this.handleChange} />
			</div>
			<div style={labelStyle}>
				<label>
					Description 
				</label>
				<textarea type="text" name="description" value={this.state.description} onChange={this.handleChange} />
			</div>
			<div style={labelStyle}>
				<label>
					Timeout
				</label>
				<input type="date" name="timeout_date" value={this.state.timeout_date} onChange={this.handleChange} />
				<input type="time" name="timeout_hour" value={this.state.timeout_hour} onChange={this.handleChange} />
			</div>
			<div style={labelStyle}>
				<label>
					Price Limit
				</label>
				<input type="number" name="pricelimit" value={this.state.pricelimit} onChange={this.handleChange} min="0" step="0.01"/>
			</div>
			<div style={labelStyle}>
				<input type="submit" value="Add Auction" />
			</div>
			 </form>
		</div>
		);
	}
}

ReactDOM.render(<AddAuctionForm />, document.getElementById('add-auction-form'));