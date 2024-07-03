import React, { useState, useEffect } from 'react';
import AddIcon from '@mui/icons-material/Add';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/DeleteOutlined';
import SaveIcon from '@mui/icons-material/Save';
import CancelIcon from '@mui/icons-material/Close';
import {
    GridRowModes,
    DataGrid,
    useGridApiRef,
    GridToolbarContainer,
    GridActionsCellItem,
    GridRowEditStopReasons,
} from "@mui/x-data-grid";
import Button from '@mui/material/Button';
import { Box, useTheme } from "@mui/material";
import { tokens } from "../../theme";
import Header from "../../components/Header";
import { Urls } from "../../api/Paths";
import useFetchData from "../../api/getData";

const Customers = () => {
    const theme = useTheme();
    const colors = tokens(theme.palette.mode);
    const url = Urls();
    const { data, loading, error } = useFetchData(url.customers.findAll_Post);
    ////////
    const [rowModesModel, setRowModesModel] = React.useState({});
    const [rows, setRows] = useState([]);
    useEffect(() => {
        if (data) {
            setRows(data);
        }
    }, [data]);


    // const EditToolbar = (props) => {
    //     const { setRows, setRowModesModel } = props;
    //
    //     const handleClick = () => {
    //         const id = 103;
    //         setRows((oldRows) => [...oldRows, {
    //             id,
    //             name: '',
    //             email: '',
    //             contactLastName: '',
    //             contactFirstName: '',
    //             phone: '',
    //             addressLine1: '',
    //             addressLine2: '',
    //             city: '',
    //             state: '',
    //             postalCode: '',
    //             country: '',
    //             creditLimit: '',
    //             employeeId: '',
    //         }]);
    //         setRowModesModel((oldModel) => ({
    //             ...oldModel,
    //             [id]: { mode: GridRowModes.Edit, fieldToFocus: 'id' },
    //         }));
    //     };
    //
    //     return (
    //         <GridToolbarContainer>
    //             <Button
    //                 sx={{
    //                     backgroundColor: colors.blueAccent[700],
    //                     color: colors.primary[100],
    //                     paddingTop: '10px', paddingRight: '10px',
    //                     paddingBottom: '10px', paddingLeft: '10px',
    //                 }}
    //                 startIcon={<AddIcon />} onClick={handleClick}>
    //                 Add record
    //             </Button>
    //         </GridToolbarContainer>
    //     );
    // }

    const apiRef = useGridApiRef();

    const EditToolbar = () => {

        const handleClick = () => {

            let idCounter = 0;
            const createRow = () => {
                idCounter += 1;
                return {
                    id: idCounter,
                    name: '',
                    email: '',
                    contactLastName: '',
                    contactFirstName: '',
                    phone: '',
                    addressLine1: '',
                    addressLine2: '',
                    city: '',
                    state: '',
                    postalCode: '',
                    country: '',
                    creditLimit: '',
                    employeeId: '',
                }
            }

            apiRef.current.updateRows([createRow()]);

        };

        return (
            <GridToolbarContainer>
                <Button
                    sx={{
                        backgroundColor: colors.blueAccent[700],
                        color: colors.primary[100],
                        paddingTop: '10px', paddingRight: '10px',
                        paddingBottom: '10px', paddingLeft: '10px',
                    }}
                    startIcon={<AddIcon />} onClick={handleClick}>
                    Add record
                </Button>
            </GridToolbarContainer>
        );
    }

    const handleRowEditStop = (params, event) => {
        if (params.reason === GridRowEditStopReasons.rowFocusOut) {
            event.defaultMuiPrevented = true;
        }
    };
    const handleEditClick = (id) => () => {
        setRowModesModel({ ...rowModesModel, [id]: { mode: GridRowModes.Edit } });
    };
    const handleDeleteClick = (id) => () => {
        setRows(rows.filter((row) => row.id !== id));
    };
    const handleSaveClick = (id) => () => {
        setRowModesModel({ ...rowModesModel, [id]: { mode: GridRowModes.View } });
    }
    const handleCancelClick = (id) => () => {
        setRowModesModel({
            ...rowModesModel,
            [id]: { mode: GridRowModes.View, ignoreModifications: true },
        });

        const editedRow = rows.find((row) => row.id === id);
        if (editedRow.isNew) {
            setRows(rows.filter((row) => row.id !== id));
        }
    };
    const processRowUpdate = (newRow) => {
        const updatedRow = { ...newRow, isNew: false };
        setRows(rows.map((row) => (row.id === newRow.id ? updatedRow : row)));
        return updatedRow;
    };
    const handleRowModesModelChange = (newRowModesModel) => {
        setRowModesModel(newRowModesModel);
    };

    const columns = [
        { field: "id", headerName: "ID", flex: 0.5, editable: true, },
        {
            field: "name", headerName: "NAME", flex: 1,
            cellClassName: "name-column--cell", editable: true
        },
        { field: "email", headerName: "EMAIL", flex: 1, editable: true },
        {
            field: "contactLastName", headerName: "CONT.LAST NAME", flex: 1,
            editable: true
        },
        {
            field: "contactFirstName", headerName: "CONT.FIRST NAME", flex: 1,
            editable: true
        },
        { field: "phone", headerName: "PHONE", flex: 1, editable: true },
        {
            field: "addressLine1", headerName: "ADRESS1", flex: 1,
            editable: true
        },
        {
            field: "addressLine2", headerName: "ADRESS2", flex: 1,
            editable: true
        },
        { field: "city", headerName: "CITY", flex: 1, editable: true },
        { field: "state", headerName: "STATE", flex: 1, editable: true },
        { field: "postalCode", headerName: "P.CODE", flex: 1, editable: true },
        { field: "country", headerName: "COUNTRY", flex: 1, editable: true },
        { field: "creditLimit", headerName: "CREDIT", flex: 1, editable: true },
        { field: "employeeId", headerName: "EMP.ID", flex: 1, editable: true },

        {
            field: 'actions',
            type: 'actions',
            headerName: 'Actions',
            width: 100,
            cellClassName: 'actions',
            getActions: ({ id }) => {
                const isInEditMode = rowModesModel[id]?.mode === GridRowModes.Edit;

                if (isInEditMode) {
                    return [
                        <GridActionsCellItem
                            icon={<SaveIcon />}
                            label="Save"
                            sx={{
                                color: 'primary.main',
                            }}
                            onClick={handleSaveClick(id)}
                        />,
                        <GridActionsCellItem
                            icon={<CancelIcon />}
                            label="Cancel"
                            className="textPrimary"
                            onClick={handleCancelClick(id)}
                            color="inherit"
                        />,
                    ];
                }

                return [
                    <GridActionsCellItem
                        icon={<EditIcon />}
                        label="Edit"
                        className="textPrimary"
                        onClick={handleEditClick(id)}
                        color="inherit"
                    />,
                    <GridActionsCellItem
                        icon={<DeleteIcon />}
                        label="Delete"
                        onClick={handleDeleteClick(id)}
                        color="inherit"
                    />,
                ];
            },
        },
    ];

    if (loading) {
        return (
            <Box m="20px">
                <Header title="CUSTOMERS" subtitle="Managing Customers" />
                <Box
                    sx={{ fontSize: "2rem" }} >
                    Loading...
                </Box>
            </Box>)
    }

    if (error) {
        return (
            <Box m="20px">
                <Header title="CUSTOMERS" subtitle="Managing Customers" />
                <Box>Error: {error.message}{}</Box>
            </Box>
        );
    }


    return (
        <Box m="20px">
            <Header title="CUSTOMERS" subtitle="Managing Customers" />
            <Box
                m="40px 0 0 0"
                height="75vh"
                sx={{
                    "& .MuiDataGrid-root": {
                        border: "none",
                    },
                    "& .MuiDataGrid-cell": {
                        borderBottom: "none",
                    },
                    "& .name-column--cell": {
                        color: colors.greenAccent[300],
                    },
                    "& .MuiDataGrid-columnHeader": {
                        backgroundColor: colors.blueAccent[700],
                        borderBottom: "none", fontSize: 14
                    },
                    "& .MuiDataGrid-virtualScroller": {
                        backgroundColor: colors.primary[400],
                    },
                    "& .MuiDataGrid-footerContainer": {
                        borderTop: "none",
                        backgroundColor: colors.blueAccent[700],
                    },
                }}
            >
                <DataGrid
                    apiRef={apiRef}
                    rows={data}
                    columns={columns}
                    editMode="row"
                    rowModesModel={rowModesModel}
                    onRowModesModelChange={handleRowModesModelChange}
                    onRowEditStop={handleRowEditStop}
                    processRowUpdate={processRowUpdate}
                    slots={{
                        toolbar: EditToolbar,
                    }}
                    slotProps={{
                        toolbar: { setRows, setRowModesModel },
                    }}
                />
            </Box>
        </Box>
    )
}

export default Customers;
