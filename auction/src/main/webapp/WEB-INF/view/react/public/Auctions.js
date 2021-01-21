const { Component } = React;
const { render } = ReactDOM;

const Auctions = () => {
    return (
		<div>
            <h1>Section for displaying all auctions</h1>
        </div>
    );
}

ReactDOM.render(<Auctions />, document.getElementById('all-auctions'));