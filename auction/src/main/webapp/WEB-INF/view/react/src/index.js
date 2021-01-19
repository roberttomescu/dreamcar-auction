import React from 'react'
import ReactDOM from 'react-dom'
import './index.css'
import Form from './Form'
import PostRequest from './PostRequest'

const inputs = [{
  name: "username",
  placeholder: "username",
  type: "text"
},{
  name: "password",
  placeholder: "password",
  type: "password"
},{
  type: "submit",
  value: "Submit",
  className: "btn" 
}]

const props = {name: 'loginForm', method: 'POST', action: '/perform_login', inputs: inputs}

const params = new URLSearchParams(window.location.search)

ReactDOM.render(<div>
					<Form {...props} error={params.get('error')} />
					<br/><br/>
					<PostRequest/>
				</div>, 
				document.getElementById('container'))

