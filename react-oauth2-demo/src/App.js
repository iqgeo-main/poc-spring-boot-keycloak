import React from 'react';

function App() {

  const handleLogin = () => {    
    window.location.href = 'http://localhost:8080/granted/oauth-login';
  }

  const hadleLogout = () => {    
    window.location.href = 'http://localhost:8080/logout';
  }

  return (
    <div className="App">
      <button onClick={handleLogin}>Login</button>
      <br />
      <button onClick={hadleLogout}>Logout</button>
      {/* alte componente */}
    </div>
  );
}

export default App;
