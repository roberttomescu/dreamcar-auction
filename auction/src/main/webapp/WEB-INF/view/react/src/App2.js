import React, { Component } from 'react';
import { HashRouter, Route, Switch } from 'react-router-dom'
import ReactDOM from 'react-dom'
 
import Homepage from './homepage'
import Navigation from './component/Navigation'

'use strict';

const e = React.createElement;
 
class App extends Component {
  render() {
	console.log("<<<debug>>> inside App component render");
    return (      
       <HashRouter>
        <div>
          <Navigation />
            <Switch>
             <Route path="/homepage" component={Homepage}/>
           </Switch>
        </div> 
      </HashRouter>
    );
  }
}

ReactDOM.render(e(App), document.getElementById('App'));