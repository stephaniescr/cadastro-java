package dao;

import factory.ConnectionFactory;
import modelo.Usuario;
import java.sql.*;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class UsuarioDAO {
	private Connection connection;
	Long id;
	String nome;
	String cpf;
	String email;
	String telefone;

	public UsuarioDAO() {
		this.connection = new ConnectionFactory().getConnection();
	}

	public int adcionarUsuario(Usuario usuario) {
		String sql = "INSERT INTO usuario (nome, cpf, email, telefone) VALUES (?, ?, ?, ?)";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
                        statement.setString(1, usuario.getNome());
                        statement.setString(2, usuario.getCpf());
                        statement.setString(3, usuario.getEmail());
                        statement.setString(4, usuario.getTelefone());
                        int inserido = statement.executeUpdate();
                        statement.close();
                        connection.close();
                        return inserido;
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

  public Usuario pesquisarUsuario(Usuario usuario) {
		//linhas comentadas pois campo Long não está funcionando corretamente com setLong
    //String sql = "SELECT nome, cpf, email, telefone FROM usuario WHERE id=?";
		String sql = "SELECT nome, cpf, email, telefone FROM usuario WHERE id="+usuario.getId();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
                        //statement.setLong(1, usuario.getId());
                        ResultSet rs = statement.executeQuery(sql);
                        if (rs.next()){
                        usuario.setNome(rs.getString(1));
                        usuario.setCpf(rs.getString(2));
                        usuario.setEmail(rs.getString(3));
                        usuario.setTelefone(rs.getString(4));
                        }
                        //statement.close();
                        rs.close();
                        statement.close();
                        connection.close();
                        return usuario;
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
  }

  public int deletarUsuario(Usuario usuario){
    String sql = "DELETE FROM usuario WHERE id=?";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
      statement.setLong(1, usuario.getId());
      int deletado = statement.executeUpdate();
			statement.close();
      statement.close();
      connection.close();
      return deletado;
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
  }

	public int alterarUsuario(Usuario usuario){
		String sql = "UPDATE usuario SET nome=?, cpf=?, email=?, telefone=? WHERE id=?";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, usuario.getNome());
			statement.setString(2, usuario.getCpf());
			statement.setString(3, usuario.getEmail());
			statement.setString(4, usuario.getTelefone());
			statement.setLong(5, usuario.getId());
			int alterado = statement.executeUpdate();
			statement.close();
			connection.close();
  		return alterado;
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

  public ArrayList<Usuario> listarUsuarios(){
		String sql = "SELECT id, nome, cpf, email, telefone FROM usuario";
		ArrayList<Usuario> listaDeUsuarios = new ArrayList<>();
		Usuario usuario;
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()){
				usuario = new Usuario(
					rs.getLong("id"),
					rs.getString("nome"),
					rs.getString("cpf"),
					rs.getString("email"),
					rs.getString("telefone")
        );
        listaDeUsuarios.add(usuario);
      }
      rs.close();
      statement.close();
      connection.close();
      return listaDeUsuarios;
      }
			catch (SQLException e) {
                throw new RuntimeException(e);
      }
		}
}
