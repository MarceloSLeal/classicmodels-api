import React, { useEffect, useState } from "react";
import axios from "axios";

// Interface para o modelo CustomerRepModel
interface CustomerRepModel {
  // Defina os tipos conforme necessário
  // ...
  name: string;
  id: number;
  email: string;
}

// Componente CustomersList
const CustomersList: React.FC = () => {
  const [customers, setCustomers] = useState<CustomerRepModel[]>([]);

  useEffect(() => {
    const fetchCustomers = async () => {
      try {
        // Faça a requisição GET para listar os clientes
        const response = await axios.get("http://localhost:8080/customers");

        // Atualize o estado com os clientes retornados
        setCustomers(response.data);
      } catch (error) {
        console.error("Erro ao buscar clientes:", error);
      }
    };

    fetchCustomers();
  }, []);

  return (
    <div>
      <h2>Lista de Clientes</h2>
      <ul>
        {customers.map((customer) => (
          <li key={customer.id}>
            {customer.name} - {customer.email}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default CustomersList;
