import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

import { Box, useTheme } from "@mui/material";
import Tooltip, { tooltipClasses } from '@mui/material/Tooltip';
import { styled } from '@mui/material/styles';

import Header from '../../components/Header';
import useFetchData from '../../api/getData';
import { Urls } from '../../api/Paths';
import { tokens } from "../../theme";
import BoxDataGrid from "../../components/boxes/BoxDataGrid"
import { GridActionsCellItem } from '@mui/x-data-grid';
import EditIcon from "@mui/icons-material/Edit";

const Payments = () => {
  const url = Urls();
  const theme = useTheme();
  const colors = tokens(theme.palette.mode);
  const { data, loading, error } = useFetchData(url.payments.findAll_Post);
  const [rows, setRows] = useState([]);
  const navigateEdit = useNavigate();


  const handleEditDataGridButton = (params) => () => {
    const rowData = params.row;
    // navigateEdit("/formeditorders", { state: { rowData } });
    // TODO -- criar tela formaddpaymentos
  }

  const GridActionTooltip = styled(({ className, ...props }) => (
    <Tooltip {...props} classes={{ popper: className }} />
  ))(() => ({
    [`& .${tooltipClasses.tooltip}`]: {
      backgroundColor: colors.primary[400],
      color: colors.primary[100],
      fontSize: 15,
      border: `1px solid ${colors.primary[100]}`,
    },
  }));

  const columns = [
    { field: "orderId", headerName: "ORDER ID", flex: 1 },
    { field: "checkNumber", headerName: "CHECK NUMBER", flex: 1 },
    { field: "paymentDate", headerName: "PAYMENT DATE", flex: 1 },
    { field: "amount", headerName: "AMOUNT", flex: 1 },
    {
      field: "actions",
      type: "actions",
      headerName: "ACTIONS",
      width: 100,
      cellClassName: "ACTIONS",
      getActions: (params) => {

        return [
          <GridActionTooltip title="Edit Payment" placement="botton">
            <GridActionsCellItem
              icon={<EditIcon />}
              label="Edit"
              className="textPrimary"
              onClick={handleEditDataGridButton(params)}
              color="inherit"
            />
          </GridActionTooltip>
        ]
      }
    }
  ]

  useEffect(() => {
    if (data) {
      setRows(data);
    }
  }, [data]);

  if (loading) {
    return (
      <Box m="20px">
        <Header title="PAYMENTS" subtitle="Manage Payments" />
        <Box
          sx={{ fontSize: "2rem" }} >
          Loading...
        </Box>
      </Box>
    )
  }

  if (error) {
    return (
      <Box m="20px">
        <Header title="Payments" subtitle="Manage Payments" />
        <Box>Error: {error.message}{ }</Box>
      </Box>
    );
  }

  return (
    <Box m="20px">
      <Header title="PAYMENTS" subtitle="Manage Payments" />

      <BoxDataGrid
        colors={colors}
        rows={rows}
        columns={columns}
      // path={"/formaddorders"}
      // TODO -- criar página formaddpayments
      />

    </Box>
  )

}

export default Payments;
