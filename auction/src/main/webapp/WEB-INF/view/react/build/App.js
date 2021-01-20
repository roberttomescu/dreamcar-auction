const { Component } = React;
const { render } = ReactDOM;

const App = () => {
    return (
		<div>
            <h1>Hello world!</h1>
        </div>
    );
}

ReactDOM.render(<App />, document.querySelector('#App'));