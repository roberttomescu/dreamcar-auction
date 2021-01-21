const { Component } = React;
const { render } = ReactDOM;


class AddAuctionForm extends React.Component{
    render() {
		return (
		<div>
            <h1>Section for adding a new auction</h1>
        </div>
    );
	}
}

ReactDOM.render(<AddAuctionForm />, document.getElementById('add-auction-form'));