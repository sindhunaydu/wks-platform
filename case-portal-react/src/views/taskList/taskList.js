import React, { useState, useEffect } from 'react';
import Button from '@mui/material/Button';
import { DataGrid, GridColDef } from '@mui/x-data-grid';
import { TaskForm } from '../taskForm/taskForm';
import { Box } from '@mui/material';

import './taskList.css';

export const TaskList = ({ tasksParam, businessKey }) => {
    const [tasks, setTasks] = useState([]);
    const [open, setOpen] = useState(false);
    const [task, setTask] = useState(null);

    useEffect(() => {
        if (!tasksParam) {
            fetch('http://localhost:8081/task' + (businessKey ? '?processInstanceBusinessKey=' + businessKey : ''))
                .then((response) => response.json())
                .then((data) => {
                    setTasks(data);
                })
                .catch((err) => {
                    console.log(err.message);
                });
        } else {
            setTasks(tasksParam.tasks);
        }
    }, [open, tasksParam, businessKey]);

    const columns: GridColDef[] = [
        { field: 'name', headerName: 'Task', width: 200 },
        { field: 'caseInstanceId', headerName: 'Case', width: 220 },
        { field: 'processDefinitionId', headerName: 'Process', width: 250 },
        { field: 'created', headerName: 'Created', type: 'date', width: 150 },
        {
            field: 'action',
            headerName: 'Action',
            sortable: false,
            renderCell: (params) => {
                const onClick = (e) => {
                    setTask(params.row);
                    e.stopPropagation(); // don't select this row after clicking
                    setOpen(true);
                };

                return <Button onClick={onClick}>Details</Button>;
            }
        }
    ];

    const handleClose = () => {
        setOpen(false);
    };

    return (
        <Box>
            <DataGrid
                sx={{ height: 650, width: '100%', backgroundColor: '#ffffff' }}
                rows={tasks}
                columns={columns}
                pageSize={10}
                rowsPerPageOptions={[10]}
            />
            <TaskForm task={task} handleClose={handleClose} open={open} />
        </Box>
    );
};