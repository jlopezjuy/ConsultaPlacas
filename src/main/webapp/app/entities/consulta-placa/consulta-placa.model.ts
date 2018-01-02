import { BaseEntity } from './../../shared';

export class ConsultaPlaca implements BaseEntity {
    constructor(
        public id?: number,
        public fecha?: any,
        public fechaTransient?: any,
        public consulta?: string,
        public metodo?: string,
        public estado?: boolean,
        public resultado?: string,
        public coordenadas?: string,
        public radioIssi?: number,
        public radioDescripcion?: string,
        public radioResponsable?: string,
        public radioMunicipio?: string,
        public radioCorporacion?: string
    ) {
        this.estado = false;
    }
}
