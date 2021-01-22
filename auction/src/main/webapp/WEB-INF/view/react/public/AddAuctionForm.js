const { Component } = React;
const { render } = ReactDOM;
const axios = axios;
const { DateTimePicker } = react-datetime-picker;


class AddAuctionForm extends React.Component{
	constructor(props) {
    super(props);
    this.state = {name: '',
				description: '',
				timeout: ''};

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleChange(event) {
	  const value = event.target.value;
	this.setState({
		...this.state, [event.target.name]: value});
  }

  handleSubmit(event) {
    alert('A bid was submitted: ' + this.state.value);
    event.preventDefault();
  }
	
    render() {
		return (	
		<div>		
			<div>
	            <h1>Section for adding a new auction</h1>
	        </div>
			<form onSubmit={this.handleSubmit}>
				<label>
					Name 
				</label>
				<input type="text" name="name" value={this.state.name} onChange={this.handleChange} />
				<label>
					Description 
				</label>
				<textarea type="text" name="description" value={this.state.description} onChange={this.handleChange} />
				<label>
					Timeout date
				</label>
				<DateTimePicker name="timeout" value={this.state.description} onChange={this.handleChange} />
			 </form>
		</div>
		);
	}
}

ReactDOM.render(<AddAuctionForm />, document.getElementById('add-auction-form'));