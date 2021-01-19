import React from 'react';

class PostRequest extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            postId: null
        };
    }

    shutdownPost() {
        // Simple POST request with a JSON body using fetch
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ title: 'Shutdown endpoint' })
        };
        fetch('http://localhost:8080/actuator/shutdown', requestOptions)
            .then(response => response.json())
            .then(data => this.setState({ postId: data.id }));
    }

    render() {
        return (
            <button onClick={this.shutdownPost()}>Shutdown</button>
        );
    }
}

export default PostRequest ; 