var update = React.addons.update;

// Store global values
var Root = React.createClass({
    getInitialState: function () {
        return {users: [], auth: [], actions: [], currentUser: null, currentAuth: null, result: ""};
    },
    componentDidMount: function () {
        $.getJSON("/ajax/user")
            .done(function (data) {
                this.setState(update(this.state, {$merge: {users: data, auth: [], actions: []}}));
            }.bind(this));
    },
    handleUserSelected: function (userId) {
        console.log("Requesting user " + userId);
        $.getJSON("/ajax/authority", {userId: userId})
            .done(function (data) {
                this.setState(update(this.state, {$merge: {auth: data, actions: [], currentUser: userId}}));
            }.bind(this));
    },
    handleAuthSelected: function (authorityId) {
        console.log("Requesting authority " + authorityId);
        $.getJSON("/ajax/activity", {authorityId: authorityId})
            .done(function (data) {
                this.setState(update(this.state, {$merge: {actions: data, currentAuth: authorityId}}));
            }.bind(this));
    },
    handleAddActivity: function (data) {
        $.post("/ajax/activity", JSON.stringify(data), "json")
            .done(function (data) {
                console.log("Activity added");
                this.setState(update(this.state, {$merge: {result: data}}));
            }.bind(this))
            .done(function (data) {
                if (this.state.currentAuth) {
                    this.handleAuthSelected(this.state.currentAuth);
                }
            }.bind(this));
    },
    render: function () {
        return (
            <div>
                <Root.UserTable data={this.state.users} onUserSelected={this.handleUserSelected}/>
                <Root.AuthorityTable data={this.state.auth} onAuthSelected={this.handleAuthSelected}/>
                <Root.ActionTable data={this.state.actions}/>
                <Root.Authenticate data={this.state.result} onActivityAdded={this.handleAddActivity}/>
            </div>
        );
    }
});

Root.UserTable = React.createClass({
    render: function () {
        return (
            <table>
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Login</th>
                </tr>
                </thead>
                <tbody>
                {this.props.data.map(function (user) {
                    return (<tr key={user.id} onClick={this.props.onUserSelected.bind(null, user.id)}>
                        <td>{user.name}</td>
                        <td>{user.login}</td>
                    </tr>);
                }.bind(this))}
                </tbody>
            </table>
        );
    }
});

Root.AuthorityTable = React.createClass({
    render: function () {
        return (
            <table>
                <thead>
                <tr>
                    <th>Role</th>
                    <th>Site</th>
                </tr>
                </thead>
                <tbody>
                {this.props.data.map(function (auth) {
                    return (<tr key={auth.id} onClick={this.props.onAuthSelected.bind(null, auth.id)}>
                        <td>{auth.site}</td>
                        <td>{auth.role}</td>
                    </tr>);
                }.bind(this))}
                </tbody>
            </table>
        );
    }
});

Root.ActionTable = React.createClass({
    render: function () {
        return (
            <table>
                <thead>
                <tr>
                    <th>Login date</th>
                    <th>Logout date</th>
                    <th>Volume</th>
                </tr>
                </thead>
                <tbody>
                {this.props.data.map(function (activity) {
                    return (<tr key={activity.id}>
                        <td>{activity.loginDate}</td>
                        <td>{activity.logoutDate}</td>
                        <td>{activity.volume}</td>
                    </tr>);
                }.bind(this))}
                </tbody>
            </table>
        );
    }
});

Root.Authenticate = React.createClass({
    getInitialState: function () {
        return {username: null, pass: null, res: null, role: null, ds: null, de: null, vol: null};
    },
    handleOnChange: function (e) {
        this.state[e.target.name] = e.target.value;
    },
    handleSubmit: function (e) {
        e.preventDefault();
        this.props.onActivityAdded(this.state);
    },
    render: function () {
        return (
            <div>
                <span id="result">{this.props.data}</span>
                <form onSubmit={this.handleSubmit}>
                    <div><label target="username">Login</label>
                        <input id="username" name="username" placeholder="jdoe" onChange={this.handleOnChange}/></div>
                    <div><label target="pass">Pass</label>
                        <input id="pass" name="pass" placeholder="sup3rpaZZ" onChange={this.handleOnChange}/></div>
                    <div><label target="res">Res</label>
                        <input id="res" name="res" placeholder="a" onChange={this.handleOnChange}/></div>
                    <div><label target="role">Role</label>
                        <select id="role" name="role" placeholder="READ" onChange={this.handleOnChange}>
                            <option value="">Select role</option>
                            <option value="READ">READ</option>
                            <option value="WRITE">WRITE</option>
                            <option value="EXECUTE">EXECUTE</option>
                        </select>
                    </div>
                    <div><label target="ds">Date start</label>
                        <input id="ds" name="ds" placeholder="2015-05-01" onChange={this.handleOnChange}/></div>
                    <div><label target="de">Date end</label>
                        <input id="de" name="de" placeholder="2015-05-02" onChange={this.handleOnChange}/></div>
                    <div><label target="vol">Volume</label>
                        <input id="vol" name="vol" placeholder="100" onChange={this.handleOnChange}/></div>
                    <div><input type="submit"/></div>
                </form>
            </div>
        );
    }
});

ReactDOM.render(
    <Root />,
    document.getElementById('container')
);
